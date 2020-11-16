# OurJupiter-backend

### application properties

```
server.address=localhost
server.port=8080

# API 호출시, SQL 문을 콘솔에 출력한다.
spring.jpa.show-sql=true

# DDL 정의시 데이터베이스의 고유 기능을 사용합니다.
# ex) 테이블 생성, 삭제 등
spring.jpa.generate-ddl=true

# MySQL 을 사용할 것.
spring.jpa.database=mysql

# MySQL 설정
spring.datasource.url=jdbc:mysql://localhost:3306/OURJUPITER?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username= //db username
spring.datasource.password= //db password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# MySQL 상세 지정
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.session.jdbc.initialize-schema: always

# Email 발송 관련
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username= //gmail 계정
spring.mail.password= //gmail 앱 패스워드
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


spring.main.allow-bean-definition-overriding=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```
