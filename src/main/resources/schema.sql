-- 시퀀스로 사용할 테이블, 시퀀스 명(name)과 해당 값(curval)으로 테이블을 생성
create table if not exists sequences
(
    name    varchar(32),
    currval bigint unsigned
)
    engine = innoDB;