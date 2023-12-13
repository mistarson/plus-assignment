-- 시퀀스로 사용할 프로시저 생성
-- 시퀀스 명을 인자로 받아 sequences 테이블에 데이터를 넣어주는 프로시저이다.
create procedure `create_sequence`(IN seq_name text)
    modifies sql data
    deterministic
begin
delete from sequences where name = seq_name;
insert into sequences values (seq_name, 0);
end;

-- 오라클 시퀀스의 기능 중 다음 값을 가져오는 nextval 함수를 만들어준다.
create function `nextval`(seq_name VARCHAR(32))
    returns bigint unsigned
    modifies sql data
    deterministic
begin
    declare ret bigint unsigned;
update sequences set currval = currval + 1 where name = seq_name;
select currval into ret from sequences where name = seq_name limit 1;
return ret;
end;

call create_sequence('seq_user');