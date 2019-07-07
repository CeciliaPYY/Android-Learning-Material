# java File
1. 输入机制与输出机制

二者都是相对于程序本身而言的。

机制 | 特点
---- | ---
输入机制 | 允许程序读入外部数据、用户输入的数据
输出机制 | 允许程序记录运行状态、将程序数据输出到磁盘、光盘等存储设备中

2. IO 流的分类

从第一种角度来看
[IO 流的第一种分类](../jpg/0707/IO流的第一种分类方法.png)

从装饰器设计模式的角度来看
[IO 流的第二种分类](../jpg/0707/IO流的第二种分类方法.png)


## File 类
### 访问文件和目录
1. 访问文件名相关的方法

方法 | 解释
---- | ---
String getName() | 返回此 File 对象所表示的文件名或路径名（如果是路径，则返回最后一级子路径名）
String getPath() | 返回此 File 对象所表示的路径名
File getAbsoluteFile() | 返回此 File 对象的绝对路径
String getAbsolutePath() | 返回此 File 对象的绝对路径名
String getParent() | 返回此 File 对象所在目录的父目录名
boolean renameTo() | 重命名此 File 对象所对应的文件或目录，若成功，则返回 true，否则返回 false

2. 文件检测相关的方法

方法 | 解释
---- | ---
boolean exists() | 判断此 File 对象所对应的文件或目录是否存在
boolean canWrite() | 判断此 File 对象所对应的文件和目录是否可写
boolean canRead() | 判断此 File 对象所对应的文件和目录是否可读
boolean isFile() | 判断此 File 对象所对应是否是文件，而不是目录
boolean isDirectory() | 判断此 File 对象所对应是否是目录，而不是文件
boolean isAbsolute() | 判断此 File 对象所对应的文件或目录是否是绝对路径

3. 获取常规文件信息

方法 | 解释
---- | ---
long lastModified() | 返回文件的最后修改时间
long length() | 返回文件内容的长度

4. 文件操作相关的方法

方法 | 解释
---- | ---
boolean createNewFile() | 当此 File 对象所对应的文件不存在时，该方法将新建一个该 File 对象所指定的新文件，若创建成功则返回 true，否则返回 false
boolean delete() | 删除 File 对象所对应的文件或目录
static File createTempFile(String prefix, String suffix) | 在默认的临时文件目录中创建一个临时的空文件，使用给定前缀、系统生成的随机数和给定后缀作为文件名。这是一个静态方法，可以直接通过 File 类来调用。 prefix 参数必须至少是3个字节长。前缀建议使用一个短的、有意义的字符串。suffix 参数可以为 null，在这种情况下，将使用默认的".temp"。
static File createTempFile(String prefix, String suffix, String directory) | 在 directory 所指定的目录中创建一个临时的空文件，使用给定前缀、系统生成的随机数和给定后缀作为文件名。这是一个静态方法，可以直接通过 File 类来调用。 
void deleteOnExit() | 注册一个删除钩子，指定当 Java 虚拟机退出时，删除 File 对象所对应的文件和目录。

5. 目录操作相关的方法

方法 | 解释
---- | ---
boolean mkdir() | 试图创建一个 File 对象所对应的目录，若创建成功，则返回 true，否则返回 false。调用该方法时 File 对象必须对应一个路径，而非一个文件。
String[] list() | 列出 File 对象的所有子文件名和路径名，返回 String 数组。
File[] listFiles() | 列出 File 对象的所有子文件和路径，返回 File 数组。
static File[] listRoots() | 列出系统所有的根路径。这是一个静态方法，可以直接通过 File 类来调用。


### 文件过滤器
File 类的 list() 方法中可以接收一个 FilenameFilter 参数，通过该参数可以只列出符合条件的文件。   

FilenameFilter 接口里包含了一个 accept(File dir, String name)方法，该方法依次对指定 File 的所有子目录或者文件进行迭代，如果该方法返回 true，则 list() 方法会列出该子目录或者文件。










