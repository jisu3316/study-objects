# 객체 설계, 티켓 판매 애플리케이션 구현하기

## 02 무엇이 문제인가
로버튼 마틴은 소프트웨어 모듈이 가져야 하는 세가지 기능에 관해 설명한다.
여기서 모듈이란 크기와 상관 없이 클래스나 패키지, 라이브러리와 같이 프로그램을 구성하는 임의의 요소를 의미한다.  

```aidl
모든 소프트웨어 모듈에는 세가지 목적이 있다. 첫번째 목적은 실행중에 제대로 동작하는 것이다. 
이것은 모듈의 존재 이유라고 할 수 있다. 두번째 목적은 변경을 위해 존재하는 것이다.
대부분의 모듈은 생명주기 동안 변경되기 때문에 간단한 작업만으로도 변경이 가능해야 한다. 
변경하기 어려운 모듈은 제대로 동작하더라고 개선해야 한다. 
모듈의 세번쨰 목적은 코드를 읽는 사람과 의사소통하는 것이다.
모듈은 특별한 훈련 없이도 개발자가 쉽게 읽고 이해할 수 있어야한다. 
읽는 사람과 의사소통할 수 없는 모듈은 개선해야 한다.
```
마틴에 따르면 모듈은 제대로 실행돼야 하고, 변경이 용이애햐 하며, 이해하기 쉬워야한ㄷ다.
chapter 01의 기존 프로그램은 관람객을 입장시키는데 필요한 기능을 오류 없ㅇ 정확하게 수행하고 있다.
따라서 제대로 실행된다는 제약은 만족시키지만 변경 용이성과 읽는 사람과의 의사소통이라는 목적은 만족시키지 못한다.


## 예상을 빗나가는 코드

Theater 클래스의 enter 메소드이다.

```java
public void enter(Audience audience) {
    if(audience.getBag().hasInvitation()) {
        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        audience.getBag().setTicket(ticket);
    } else {
        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        audience.getBag().minusAmount(ticket.getFee());
        ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        audience.getBag().setTicket(ticket);
    }
}
```
위의 메소드가 하는 행동을 말로 풀어보자

```java
소극장은 관람객의 가방을 열어 그 안에 초대장이 들어 있는지 살펴본다. 
가방 안에 초대장이 들어 있으면 판매원은 매표소가 보관돼 있는 티켓을 관람객의 가방안으로 옮긴다.
가방 안에 초대장이 들어 있지 않다면 관람객의 가방에서 티켓 금액만큼의 현금을 꺼내 매표소에 적립한 후에 매표소에 보관돼 있는 티켓을 관람객의 가방 안으로 옮긴다.
```

## 문제점
관람객과 판매원이 소극장의 통제를 받는 수동적인 존재라는 점이다.
내가 관람객인데 소극장이라는 제 3자가 초대장을 확인하기 위해 내 가방을 마음대로 열어보고 있는 것이다.
내가 판매원이라고 해도 문제가 생긴다. 소극장이 내 허락도 없이 매표소에 보관중인 티켓과 현금에 마음대로 접근한다.

또 다른 문제점은 코드를 읽는 사람과 의사소통하지 못한다.
코드를 이해하기 위해서는 여러가지 세부적인 내용들은 한꺼번에 기억하고 있어야 한다는 점이다.
Theater의 enter 메서드를 이해하기 위해ㅔ서는 Audience가 Bag을 가지고 있고, Bag안에는 현금과 티켓이 들어 있으며 
TicketSeller가 TicketOffice에서 티켓을 판매하고 TicketOffice안에 돈과 티켓이 보관돼 있다는 모든 사실을 동시에 기억하고 있어야한다.

하지만 가장 심각한 문제는 Audience와 TicketSeller를 변경할 경우 Theater도 함께 변경해야 한다는 사실이다.


## 변경에 취약한 코드

이것은 객체 사이에 의존성(dependency)과 관련된 문제다. 문제는 의존성이 변경과 관련돼 있다는 점이다.
의존성이라는 말 속에는 어떤 객체가 변경될 때 그 객체에게 의존하는 다른 객체도 함께 변경될 수 있다는 사실이 내포돼 있다.

그렇다고 해서 객체 사이의 의존성을 완전히 없애는 것이 정답은 아니다. 객체지향 설계는 서로 의존하면서 협력하는 객체들의 공동체를 구축하는 것이다.
따라서 우리의 목표는 애플리케이션의 기능을 구현하는데 필요한 최소한의 의존성만 유지하고 불필요한 의존성을 제거하는 것이다.

객체 사이의 의존이 과한 경우를 가리켜 **결함도(coupling)** 가 높다고 말한다.
반대로 객체들이 합리적인 수준으로 의존할 경우에는 결합도가 낮다고 말한다.
결합도는 의존성과 관련돼 있기 때문에 결합도 역시 변경과 관련이 있다. 
두 객체사이의 결합도가 높으면 높을수록 함께 변경될 확률도 높아지기 때문에 변경하기 어려워진다. 
따라서 설계의 목표는 객체 사이의 결합도를 낮춰 변경이 용이한 설계를 만드는 것이어야한다.


## 03 설계 개선하기

Theater가 관람객의 가방과 판매원의 매표소에 직접 접근한다는 것은 Theater가 Audience와 TicketSeller에 결합된다는 것을 의미한다.
따라서 Audience와 TicketSeller를 변경할 때 Theater도 함께 변경해야 하기 때문에 전체적으로 코드를 변경하기도 어려워진다.

해결 방법은 Theater가 Audience와 TicketSeller에 관해 너무 세세한 부분까지 알지 못하도록 정보를 차단하면 된다.
따라서 관람객이 스스로 가방 안의 현금과 초대장을 처리하고 판매원이 스스로 매표소의 티켓과 판매 요금을 다루게 한다면 이 모든 문제를 한 번에 해결할 수 있다.

다시 말해서 관람객과 판매원을 **자율적인 존재**로 만들면 되는 것이다.

### 자율성을 높이자
설계를 변경하기 어려운 이유는 Theater가 Audience와 TicketSeller 뿐만 아니라 Audience 소유의 Bag과 TicketSeller가 근무하는 TicketOffice까지 
마음대로 접근할 수 있기 때문이다.

첫번째 단계는 Theater의 enter 메서드에서 TicketOffice에 접근하는 모든 코드를 TicketSeller 내부로 숨기는 것이다.
TicketSeller에 sellTo 메서드를 추가하고 Theater에 있던 로직을 옮긴다.

```java
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        ticketSeller.sellTo(Audience audience);
    }
}

public class TicketSeller {
    private TicketOffice ticketOffice;
    
    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }
    
    public void sellTo(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketOffice.plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```

TicketSeller에서 getTicketOffice 메서드가 제거되었다. 
ticketOffice의 가시성이 private이고 접근 가능한 퍼블릭 메서드가 더 이상 존재하지 않기 때문에 외부에서는 ticketOffice에 직접 접근할 수 없다.
ticketOffice에 대한 접근은 오직 TocketSeller 안에만 존재하게 된다.
따라서 TicketSeller는 TicketOffice에서 티켓을 꺼내거나 판매 요금을 적립하는 일을 스스로 수행할 수 밖에 없다.

이처럼 개념적이나 물리적으로 객체 내부의 세부적인 사항을 감추는 것을 **캡슐화(encapsulation)** 라고 부른다.
캡슐화의 목적은 변경하기 쉬운 객체를 만드는 것이다. 캡슐화를 통해 객체 내부로의 접근을 제한하면 객체와 객체사이의 결합도를 낮출 수 있기 때문에 
설계를 좀 더 쉽게 변경할 수 있게 된다.

수정된 Theater 클래스 어디서도 ticketOffice에 접근하지 않는다.
Theater는 ticketOffice가 ticketSeller 내부에 존재한다는 사실을 알지 못한다.
Theater는 단지 tickeetSeller가 sellTo 메시지를 이해하고 응답할 수  있다는 사실만 알고 있을 뿐이다.

Theater는 오직 TicketSeller의 인터페이스에만 의존한다. 
TicketSeller가 내부에 TicketOffice 인스턴스를 포함하고 있다는 사실은 구현의 영역에 속한다.
객체를 인터페이스와 구현으로 나누고 인터페이스만을 공개하는 것은 객체 사이의 결합도를 낮추고 변경하기 쉬운 코드를 작성하기 위해 따라야 하는 가장 기본적인 설계이다.

그 다음으로는 Audience를 개선해보자

```java

public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Long buy(Ticket ticket) {
        if (bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        } else {
            bag.setTicket(ticket);
            bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }
}

public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }
}
```
buy 메서드는 인자로 전달된 Ticket을 Bag에 넣은 후 지불된 금액을 반환한다.
변경된 코드에서 Audience는 자신의 가방 안에 초대장이 들어있는지를 스스로 확인한다.
외부의 제 3자가 자신의 가방을 열어보는것을 허용하지 않는다. Audience가 Bag을 직접 처리하기 때문에 외부에서는 더 이상
Audience가 Bag을 소유하고 있다는 사실을 알 필요가 없다. 
이제 Audience 클래스에서 getBag() 메서드를 제거할 수 있고 결과적으로 Bag의 존재를 내부로 캡슐화할 수 있게 됐다.

TiekcetSeller가 Audience의 인터페이스에만 의존하도록 수정하였다.
코드를 수정한 결과, TicketSeller와 Audience사이의 결합도가 낮아졌다.
또한 내부 구현이 캡슐화 됐으니 Audience의 구현을 수정하더라고 TicketSeller에는 영향을 미치지 않는다.
캡슐화를 개선한 후에 가장 크게 달라진점은 Audience와 TicketSeller가 내부 구현을 외부에 노출하지 않고 자신의 문제를 스스로 책임지고 해결한다는 것이다.
다시 말해 자율적인 존재가 된 것이다.

### 무엇이 개선됐는가
수정된 예제 역시 관람객을 입장시키는데 필요한 기능을 오류없이 수행한다.
따라서 마틴 파울러가 말한 모듈의 기준에서 동작을 수행해야 한다는 첫번째 목적을 만족 시킨다.
그렇다면 변경 용이성과 의사소통은 어떨까?

수정된 Audience와 TicketSeller는 자신이 가지고 있는 소지품을 스스로 관리한다.
따라서 코드를 읽는 사람과의 의사소통이라는 관점에서 이 코드는 확실히 개선된 것으로 보인다.
더 중요한 점은 Audience나 TicketSeller의 내부 구현을 변경하더라고 Theater를 함께 변경할 필요가 없어졌다는 것이다.
Audience가 가방이 아니라 작은 지갑을 소지하도록 하고 싶으면 Audience 내부만 변경하면 된다.
TicketSeller가 매표소가 아니라 은행에 돈을 보관하고 싶도록 만들고 싶으면 TicketSeller 내부만 변경하면 된다.
두 경우 모두 변경은 Audience와 TicketSeller 내부만으로 제한된다.
따라서 수정된 코드는 변경 용이성의 측면에서도 확실히 개선됐다고 말할 수 있다.

### 어떻게 한 것인가
판매자가 티켓을 판매하기 위해 TicketOffice를 사용하는 모든 부분을 TicketSeller 내부로 옮기고,
관람객이 티켓을 구매하기 위해 Bag을 사용하는 모든 부분을 Audience 내부로 옮긴 것이다.
다시 말해 자기 자신의 문제를 스스로 해결하도록 코드를 변경한 것이다.

수정하기 전의 코드는 Theater가 Audience와 TicketSeller의 상세한 내부 구현까지 알고 있어야 됐기 때문에
강하게 결합 되어 있었고, 그 결과 Audience와 TicketSeller의 사소한 변경에도 Theater가 영향을 받을 수 밖에 없었다.

수정한 후의 Theater는 Audience나 TicketSeller의 내부에 직접 접근하지 않는다.
Audience는 Bag내부의 내용물을 확인하거나 추가하거나 제거하는 작업을 스스로 처리하며 누군가에게 자신의 가방을 열어보도록 허용하지 않는다.
객체의 자율성을 높이는 방향을 설계를 개선했다. 그 결과, 이해하기 쉽고 유연한 설계를 얻을 수 있었다.


### 캡술화와 응집도
핵심은 객체 내부의 상태를 캡슐화하고 객체 간에 오직 메시지를 통해서만 상호작용하도록 만드는 것이다.
Theater는 TickteSeller의 내부에 대해서는 전혀 알지 못한다. 단지 TicketSeller가 sellTo 메시지를 이해하고 응답할 수 있단느 사실만 알고 있다.
TicketSeller 역시 Audience의 내부에 대해서는 전혀 알지 못한다. 
단지 Audience가 buy 메시지에 응답할 수 있고 자신이 원하는 결과를 반환할 것이라는 사실만 알고 있을 뿐이다.
밀접하게 연관된 작업만을 수행하고 연관성 없는 작업은 다른 객체에게 위임하는 객체를 가리켜 **응집도(cohession)** 가 높다고 말한다.
자신의 데이터를 스스로 처리하는 자율적인 객체를 만들면 결합도를 낮출 수 있을 뿐더러 응집도를 높일 수 있다.
***객체의 응집도를 높이기 위해서는 객체 스스로 자신의 데이터를 책임져야  한다.***
객체는 자신의 데이터를 스스로 처리하는 자율적인 존재여햐 한다. 그것이 객체의 응집도를 높이는 첫 걸음이다.
외부의 간섭을 최대한 배제하고 메시지를 통해서만 협력하는 자율적인 객체들의 공동체를 만드는 것이 훌륭한 객체지향 설계를 얻을 수 있는 지름길인 것이다.


### 절차지향과 객체지향
수정하기 전의 코드에서는 Theater의 enter 메서드 안에서 Audience와 TicketSeller로 부터 Bag과 TickeetOffice를 가져와 
관람객을 입장시키는 절차를 구현했다. 

이 관점에서 Theater의 enter 메서드는 프로세스(Process)이며 Audience, TicketSeller, Bag, TicketOffice는 데이터다. 
이처럼 프로세스와 데이터를별도에 위치시키는 방식을 절차적 프로그래밍이라고 부른다.

수정한 코드에서 데이터를 사용하는 프로세스가 데이터를 소유하고 있는 Audience와 TicketSeller 내부로 옮겼다. 
이처럼 데이터와 프로세스가 동일한 모듈내부에 위치하도록 프로그래밍하는 방식을 **객체지향 프로그래밍(Objecct-Oriented Programming)** 이라고 부른다.
훌륭한 객체지향 설계의 핵심은 캡슐화를 이용해 의존성을 적절히 관리함으로써 객체 사이의 결합도를 낮추는 것이다.

### 책임의 이동
절차 지향과 객체 지향 두 방식 사이에 근본적인 차이를 만드는 것은 **책임의 이동**이다.
두 방식의 차이점을 쉽게 이해할 수 있는 방법은 기능을 처리하는 방법을 살펴보는 것이다. 
변경 전의 절차적 설계에서는 Theater가 전체적인 작업을 도맡아 처리했다. 변경 후의 객체지향 설계에서는 각 객체가 자신이 맡은 일을 스스로 처리했다. 
다시 말해 Theater에 몰려 있던 책임이 개별 객체로 이동하였고 이것을 **책임의 이동**이 의미하는 것이다.
객체 지향 설계의 핵심은 적잘한 객체에 적절한 책임을 할당하는 것이다. 객체는 다른 객체와의 협력이라는 문맥안에서 특정한 역할을 수행하는데 필요한 적절한 책임을 수행해야 한다. 
따라서 객체가 어떤 데이터를 가지느냐보다는 객체에 어떤 책임을 할당할 것이냐에 초점을 맞춰야한다.

설계를 어렵게 만드는 것은 의존성이라는 것이다. 해결 방법은 불필요한 의존성을 제거함으로써 객체 사이의 결합도를 낮추는 것이다.
예제에서 결합도를 낮추기 위해 선택한 방법은 Theater가 몰라도 되는 세부사항을 Audience와 TicketSeller 내부로 감춰 **캡슐화**하는 것이다.
결과적으로 불필요한 세부사항을 객체 내부로 캠슐화하는 것은 객체의 자율성을 높이고 응집도 높은 객체들의 공동체를 만들 수 있게 된다.


### 더 개선하자

```java
import com.jisu._01_ticket.Invitation;
import com.jisu._01_ticket.Ticket;

public class Bag {
    private Long amount;
    private Ticket ticket;
    private Invitation invitation;

    public Bag(Long amount, Ticket ticket, Invitation invitation) {
        this.amount = amount;
        this.ticket = ticket;
        this.invitation = invitation;
    }

    public Long hold(Ticket ticket) {
        if (hasInvitation()) {
            setTicket(ticket);
            return 0L;
        } else {
            setTicket(ticket);
            minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    // 초대장 보유 여부 판단
    private boolean hasInvitation() {
        return invitation != null;
    }
}
```

Bag을 자율적인 존재로 바꾸었다.
Bag의 내부 상태에 접근하는 모든 로직을 Bag안으로 캡슐화해서 결합도를 낮추었다.


```java
public class Audience {
    public Long buy(Ticket ticket) {
        return bag.hold(ticket);
    }
}

public class TicketSeller {
    public void sellTo(Audience audience) {
        ticketOffice.sellTicketTo(audience);
    }
}

public class TicketOffice {
    public void sellTicketTo(Audience audience) {
        plusAmount(audience.buy(getTicket()));
    }

    public Ticket getTicket() {
        return tickets.remove(0);
    }

    //판매금액을 더한다.
    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
```

Audience 또한 Bag의 인터페이스에만 의존하도록 수정하였다.
TicketSeller는 TicketOffice의 sellTicketTo 메서드를 호출함으로써 원하는 목적을 달성할 수 있다.
이제 TicketSeller가 TicketOffice의 구현이 아닌 인터페이스에만 의존하게 됐다는 점이다.  

하지만 위의 코드도 만족스럽지 않다고 한다. TicketOffice와 Audience사이에 의존성이 추가 되었기 때문이다.  
변경 전에는 TicketOffice가 Audience에 대해 알지 못했었다. 
변경 후에는 TicketOffice가 Audience에게 직접 티켓을 판매하기 때문에 Audience에 관해 알고 있어야 한다.
TicketOffice의 자율성은 높였지만 전체적인 설계의 관점에서는 결합도가 상승했다.  

이 작은 예제를 통해 우리는 두 가지 사실을 알게 됐다.
첫째, 어떤 기능을 설계하는 방법은 한가지 이상일 수 있다. 
둘떄 동일한 기능을 한 가지 이상의 방법으로 설계할 수 있기 때문에 결국 설계는 트레이드오프의 산물이다.
어떤 경우에도 모든 사람들을 만족시킬수 있는 설계를 만들 수는 없다.
훌륭한 설계는 적절한 트레이드오프의 결과물이라는 사실을 명심해야한다.

현실세계에서는 수동적인 존재라고 해도 객체지향의 세계에 들어오면 모든 것이 능동적이고 자율적인 존재로 바뀐다.
따라서 이해하기 쉽고 변경하기 쉬운 코드를 작성하고 싶다면 차라리 한 편의 애니메이션을 만든다고 생각해야한다.


### 객체지향 설계

설계란 코드를 배치하는 것이다.

좋은 설계란 무엇인가?
우리가 짜는 프로그램은 두 가지 요구사항을 만족시켜야 한다. 우리는 오늘 완성해야 하는 구현하는 코드를 짜야 하는 동시에 내일 쉽게 변경할 수 있는 코드를 짜야 한다.
좋은 설계란 오늘 요구하는 기능을 온전히 수행하면서 내일의 변경을 매끄럽게 수요할 수 있는 설계다.

따라서 우리가 진정으로 원하는 것은 변경에 유연하게 대응할 수 있는 코드다.
변경 가능한 코드란 이해하기 쉬운 코드다. 코드를 변경해야 하는데 그 코드를 이해할 수 없다면 변경하기 쉽지 않다.