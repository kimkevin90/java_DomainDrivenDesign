# 초기 mysql 셋팅
- docker-compose -p order-dev up -d
- docker-compose -p gift-db up -d
- docker-compose down --volume

# 대체키와 DIP
1. 대체키
- 엔티티의 식별자(PK)는 UUID로 생성
- 식별자가 AI 일 경우 유추 가능 문제 발생
- UUID는 1천만건 이상 부터 성능 이슈 발생하는데, 시간값을 앞에 설정하면 이러한 부분을 완화함
2. 의존성 역전 원칙
- 오더서비스가 각 구현체에 의존하게 되면, 오더 서비스의 flow들의 각 항 목 구현체가 변경되면 flow자체에 코드수정이 불가피하다.
- 의존성 역전 원칙은 구현체가 오더서비스에 의존하도록 하게한다.
- 오더서비스는 인터페이스를 따로 두고 해당 인터페이스에 의존한다 (오더서비스 -> 인터페이스) ~ Domain Layer
- 구현체는 오더Impl을 생성후 orderRepository에 의존한다(Impl -> Repository) - infra Layer
- 결국 인터페이스와 Impl만 서로 상호작용하므로 기존의 서비스와 구현체 영역이 분리됨에 따라 결합도를 낮춘다.

# 1. Partner Domain
## Entity, Service 구현

<img width="846" alt="image" src="https://user-images.githubusercontent.com/65535673/194752725-b3587939-f75c-402d-9715-280c9f385426.png">

## Application, Interface 개요
- transaction으로 묶여야 하는 도메인 로직과 그 외 로직을 arrregation 하는 역할로 한정 짓는다.(Facade 패턴 적용)
- 결국 응용 계층을 하나 더 둠으로써 도메인 계층에서 처리하기 애매한 요구사항을 충족할 수있는 여유가 생긴다.
- 외부 인터페이스 계층 구현은 표준을 정의하고 그에 맞게 외부 호출과 응답이 정의되도록 구현해야 한다.

# 2. Item Domain
## Entity 구현
- Item / ItemOptionGroup / ItemOption으로 나눈 후 1:N관계를 형성한다.
- Item에만 외부에서 접근하면 ItemOptionGroup & ItemOption으로은 Item domain 내 에서 접근한다.
- 따라서 Item에만 random 대체키를 제공한다.

# 3. Order Domain
## Entity 구현
- Order(root) -> OrderItem -> OrderItemOptionGroup -> OrderItemOption
1) 주문의 전체 가격은 상품의 가격 * 주문 갯수 이다. => Order에서 OrderItem의 List를 뽑아서 각각의 상품별 가격을 sum한다.
2) Order의 배송정보(수령인, 주소)는 각각 따로 생성하지만 하나의 정보로만 의미가 있으므로 @Embedded를 사용한다.
3) OrderItem/OrderItemOptionGroup/OrderItemOption 3가지는 Factory를 만들고 Infra Layer에서 Impl 구현
4) 


https://github.com/gregshiny/example-gift 
