# 字符串处理

## 6.1 遍历字符串

### 问题

例如，你想把 EMP 表的 ENAME 等于 KING 的字符串拆开来显示为 4 行，每行一个字符。



### 解决方案

使用笛卡尔积。

```
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
```



## 6.3 统计字符出现的次数

### 问题

你想统计某个字符或者子字符串在给定字符串里出现的次数，考虑如下的字符串。

10, CLARK, MANAGER

你想知道该字符串里有多少个逗号。



### 解决方法

使用 REPLACE 函数。

```
SELECT 
(length("10, CLARK, MANAGER") 
- length(REPLACE("10, CLARK, MANAGER", ",", "")) ) / length(",")
as cnt from t1
```

**Note**：最后的除法运算是用上述两个长度的差值除以我们正在搜索的那个字符串长度。如果被搜索的字符串的长度大于1的话，就**必须使用除法运算**。看下面这个例子，

```
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
```

结果如下，

| **correct_cnt** | **incorrect_cnt** |
| --------------- | ----------------- |
| 2               | 4                 |



## 6.5 分离数字和字符数据

待解决



## 6.7 提取姓名的首字母

### 问题

你想把姓名变成首字母的形式，考虑人名 Stewie Griffin，你希望得到 S.G.。



### 解决方法

SUBSTRING_INDEX

```
SELECT concat(substring(SUBSTRING_INDEX("Stewie Killing Griffin", " ", 1), 1, 1 ), ".",
substring(SUBSTRING_INDEX(SUBSTRING_INDEX("Stewie Killing Griffin", " ", 2), " ", -1), 1, 1), ".",
substring(SUBSTRING_INDEX("Stewie Killing Griffin", " ", -1), 1, 1), ".")
from t1;
```

