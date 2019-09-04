SELECT * from dept;


SELECT * from emp;

SELECT * from dept 
left OUTER join emp
on dept.deptno = emp.deptno;

SELECT * from dept 
right OUTER join emp
on dept.deptno = emp.deptno;

select * from 
(SELECT dept.deptno, 
dept.dname, emp.ename from dept 
left OUTER join emp
on dept.deptno = emp.deptno) res1
union 
(SELECT dept.deptno, 
dept.dname, emp.ename from dept 
right OUTER join emp
on dept.deptno = emp.deptno) 

SELECT * from emp;

SELECT * 
from emp
where coalesce(comm, 0) <
(
    SELECT comm from emp
where ename = "WARD"
)

select * from emp
WHERE ename = "KING";





drop TABLE t1;


CREATE TABLE t1
(
    ID int(11) NOT NULL auto_increment PRIMARY KEY
    -- specify more columns here
);



DELIMITER ;;
CREATE PROCEDURE dowhile()
BEGIN
  DECLARE v1 INT DEFAULT 10; -- 创建t10
  WHILE v1 > 0 DO
    INSERT t1 VALUES (NULL);
    SET v1 = v1 - 1;
  END WHILE;
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS dowhile;
CALL dowhile();
SELECT * FROM t1;


CREATE TABLE t10
(
    ID int(11) NOT NULL auto_increment PRIMARY KEY
    -- specify more columns here
);

DELIMITER ;;
CREATE PROCEDURE dowhile10()
BEGIN
  DECLARE v1 INT DEFAULT 10; -- 创建t10
  WHILE v1 > 0 DO
    INSERT t10 VALUES (NULL);
    SET v1 = v1 - 1;
  END WHILE;
END;;
DELIMITER ;

CALL dowhile10();
SELECT * FROM t10;



select emp.ename,  t10.id as pos, 
substring(emp.ename, t10.id, 1) as letter
from emp, t10
WHERE ename = "KING"
and t10.id <= length(emp.ename)


drop TABLE t1;
CREATE TABLE t1
(
    ID int(11) NOT NULL auto_increment PRIMARY KEY
    -- specify more columns here
);

DELIMITER ;;
CREATE PROCEDURE dowhile1()
BEGIN
  DECLARE v1 INT DEFAULT 1; -- 创建t10
  WHILE v1 > 0 DO
    INSERT t1 VALUES (NULL);
    SET v1 = v1 - 1;
  END WHILE;
END;;
DELIMITER ;

CALL dowhile1();
SELECT * FROM t1;


SELECT 'g''day mate' QMARKS from t1
UNION 
SELECT 'beavers'' teeth' QMARKS from t1
UNION
SELECT '''' QMARKS from t1


SELECT length("10, CLARK, MANAGER") as cnt from t1

-- REPLACE(str,from_str,to_str) 

SELECT res1.correct_cnt, res2.incorrect_cnt
from
(SELECT 
(length("Hello Hello") 
- length(REPLACE("Hello Hello", "ll", "")) ) / length("ll")
as correct_cnt from t1) res1,
(
    SELECT 
    (length("Hello Hello") 
    - length(REPLACE("Hello Hello", "ll", "")) ) 
    as incorrect_cnt from t1
) res2

SELECT ename, sal from emp

SELECT ename, 
replace(
    replace(
        replace(
            replace(
                replace(ename, "A", ""), 
                    "E", ""), 
                    "I", ""), 
                    "O", ""),
                    "U", "") as stripped1, sal,
                    replace(sal, 0, "") as stripped2
from emp    


SELECT concat(ename, sal) as mess from emp

SELECT max(length(concat(ename, sal))) as max_length from emp


create view V2 as
    select ename as data
    from emp
    where deptno=10
    union all
    select concat(ename, ', $', cast(sal as char(4)), '.00') as data
    from emp
    where deptno=20
    union all
    select concat(ename, cast(deptno as char(4))) as data
    from emp
    where deptno=30

SELECT data from V2
WHERE not data regexp "[^0-9a-zA-Z]";


-- length = 2
SELECT concat(substring(name, 1, 1), ".",
substring(name, res3.firstLocation+1, 1), ".") 
from 
(
    SELECT name, firstLocation 
    from 
    (
        SELECT LOCATE(" ", "Stewie Griffin") as firstLocation,
        res1.name as name
        from 
        (SELECT "Stewie Griffin" as name from t1) res1
    ) res2
)res3

-- length = 3

SELECT concat(substring(name, 1, firstLocation - 1), ".",
substring(name, firstLocation + 1, secondLocation - firstLocation - 1),".",
substring(name, secondLocation + 1, length(name)))
from 
(
SELECT firstLocation, name, 
LOCATE(" ", substring(name, firstLocation+1, length(name))) + firstLocation  as secondLocation
from 
(
SELECT LOCATE(" ", "Stewie Killing Griffin") as firstLocation,
res1.name as name
from 
(SELECT "Stewie Killing Griffin" as name from t1) res1
)res2
) res3

SELECT concat(substring(SUBSTRING_INDEX("Stewie Killing Griffin", " ", 1), 1, 1 ), ".",
substring(SUBSTRING_INDEX(SUBSTRING_INDEX("Stewie Killing Griffin", " ", 2), " ", -1), 1, 1), ".",
substring(SUBSTRING_INDEX("Stewie Killing Griffin", " ", -1), 1, 1), ".")
from t1;

SELECT ename, substring(ename, length(ename) - 1, 2)  from emp
ORDER By substring(ename, length(ename) - 1, 2) 

create view V3 as
     select concat(e.ename, ' ',
            cast(e.empno as char(4)),' ',
            d.dname) as data
       from emp e, dept d
      where e.deptno=d.deptno

select * from V3

select ename 
from 
(SELECT e1.deptno, e2.ename 
from emp e1 JOIN emp e2
on e1.deptno = e2.deptno
WHERE e1.deptno is not NULL 
GROUP BY e1.deptno, e2.ename
ORDER BY e1.deptno) res1
 WHERE res1.deptno = 10

      








