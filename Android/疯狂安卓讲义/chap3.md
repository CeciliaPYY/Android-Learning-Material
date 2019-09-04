# Android的事件处理

## 目录

- 3.1 事件处理概述与Android事件处理
- 基于监听的事件处理模型
  - 事件与事件监听器接口
  - 实现事件监听器的方式
- 基于回调的事件处理模型
  - 基于回调的事件传播
  - 常见的事件回调方法
- 响应系统设置事件
- 重写 onConfigurationChanged 方法响应系统设置更改
- Handler 类的功能与用法
- 使用 Handler 更新程序界面
- Handler、Looper、MessageQueue 工作原理
- 异步任务的功能与用法



##3.1 Android 事件处理概述

| 事件模型           | 做法                       | 使用场景/特点                                                |
| ------------------ | -------------------------- | ------------------------------------------------------------ |
| 基于监听的事件处理 | 为组件绑定特定的事件处理器 | 具有通用性的事件，比如点击。分工更明确，事件源和监听器由两个类分开实现，具有更好的维护性；基于监听的事件监听器会被优先触发。 |
| 基于回调的事件处理 | 重写组件的回调方法         | 处理代码比较简洁，更具有内聚性。更适合于事件处理逻辑比较固定的View。 |





## 3.2 基于监听的事件处理

### 3.2.1 监听的处理模型

模型中主要涉及3个对象，

- 事件源：一般指的是各个组件；
- 事件：通常是一次用户操作；
- 事件监听器：负责对事件源上发生事件作出相应；

我们知道Java是面向对象的编程语言，方法是无法独立存在的，因此事件监听器的核心就是它的方法——这些方法可以叫做”事件处理器“。

![image-20190731170833859](/Users/pengyuyan/Documents/Android-Learning-Material/Android/疯狂安卓讲义/images/image-20190731170833859.png)

事件、事件源与事件监听器之间的关系。

![image-20190731171021923](/Users/pengyuyan/Documents/Android-Learning-Material/Android/疯狂安卓讲义/images/image-20190731171021923.png)

举个基于监听的事件处理例子，

```
public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.testButton);
        et = findViewById(R.id.editText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("按钮被点击了");
            }
        });

    }
    
}
```



目前 View 类有以下监听器接口，

| 监听器接口                         | 触发的事件         |
| ---------------------------------- | ------------------ |
| View.OnClickListener()             | 单击事件           |
| View.OnCreateContextMenuListener() | 创建上下文菜单事件 |
| View.OnFocusChangeListener()       | 焦点改变事件       |
| View.OnKeyListener()               | 按键事件           |
| View.OnLongClickListener()         | 长按事件           |
| View.OnTouchListener()             | 触摸事件           |



以下几种方式都可以实现事件监听器，

- 内部类形式
- 外部类形式
- Activity 本身作为事件监听器类
- 匿名内部类形式
- 直接绑定到标签

它们各有自己的应用场景与优势。

| 事件监听器                    | 应用场景与优势                                               |
| ----------------------------- | ------------------------------------------------------------ |
| 内部类形式                    | 可以在当前类中复用；可以自由访问外部类的所有界面组件；       |
| 外部类形式                    | 这种形式比较少见。主要有以下两个原因：1. 事件监听器属于特定的GUI页面，定义成内部类不利于提高程序的内聚性；2. 外部类的事件监听器无法自有访问创建GUI页面的类的组件，编程不简洁。因此其使用场景主要是，某个事件监听器的确需要被多个GUI页面共享，完成某种业务逻辑的实现。 |
| Activity 本身作为事件监听器类 | 方法简洁但程序结构混乱                                       |
| 匿名内部类形式                | 使用最广泛的方法                                             |
| 直接绑定到标签                | 在xml文件中添加android:onClick属性，属性值为具体方法，然后实现该方法 |



## 3.3 基于回调的事件处理

对于基于回调的事件模型来说，事件与事件监听器是统一的，或者说事件监听器完全消失了。用户在GUI组件上激发某个事件时，组件自己的特定方法会负责处理该事件。

那么具体的实现方法又是怎么样的呢？

为了使用回调机制来处理GUI组件上所发生的事件，需要为GUI组件提供对应事件的应对方法，但是Java又是一种静态语言，无法动态添加方法，因此我们只能继承GUI组件类，并重写该类的事件处理方法来实现。



### 3.3.2 基于回调事件的传播

几乎所有基于回调的事件处理方法都有一个boolean返回值，该返回值用于标识该方法是否能完全处理该事件。

- true：处理方法已完全处理该事件，事件不会传播出去；
- false：处理方法尚未完全处理该事件，事件会传播出去；

如果事件传播出去，这个组件上所发生的事件不仅会引发该组件上的回调方法，也会触发组件所在Activity的回调方法。





## 3.4 响应系统设置事件

### 3.4.1 Configuration 类简介

#### 获取方法

`Configuration cfg = getResources().getConfiguration();`



#### 常用属性

fontScale、keyboard、keyboardHidden、locale、mcc、mnc等



看一个小例子，下面例子实现了监听屏幕方向的改变。

```
public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.testButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration cfg = getResources().getConfiguration();
                if (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    MainActivity2.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                if (cfg.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    MainActivity2.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newCfg) {
        super.onConfigurationChanged(newCfg);
        String screen = newCfg.orientation ==
                Configuration.ORIENTATION_LANDSCAPE ? "landscape" : "portrait";
        Toast.makeText(this, screen, Toast.LENGTH_SHORT);
    }
}
```



此外还要在AndroidManifest.xml中指定android:configChanges属性（可以同时指定多个属性）。



## 3.5 Handler 消息传递机制

