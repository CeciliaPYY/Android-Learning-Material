## 在运算和比较中使用 Null

### 问题

你想找出 EMP 表里业务提成(COMM列)比员工 WARD 低的所有员工。检索结果应该包含业务提成为 Null 的员工。



### 解决方法

使用如 **COALESCE** 这样的函数把 Null 转换为一个具体的、可以用于标准评估的值。

```
SELECT * 
from emp
where coalesce(comm, 0) <
(
    SELECT comm from emp
where ename = "WARD"
)
```

