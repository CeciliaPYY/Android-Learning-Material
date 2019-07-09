# 分支

假设现在有一个工作目录，里面包含了三个将要被暂存和提交的文件。

   ​git add README test.rb LICENSE
   ​git commit -m 'The initial commit of my project'

当使用 git commit 进行提交操作之后，Git 仓库中有五个对象：

- 三个 blob 对象(保存着文件快照)
- 一个树对象(记录着目录结构和 blob 对象索引)
- 一个提交对象(包含着指向前述树对象的指针和所有提交信息)。

[首次提交对象及其树结构](../jpg/首次提交对象及其树结构.png)做些修改后再次提交，此次产生的提交对象会包含指向上一次提交对象（父对象）的指针。[提交对象及其父对象](../jpg/提交对象及其父对象.png)



## 分支创建

```
$ git branch testing
```

git 是通过创建一个可以移动的新指针来创建分支的[两个指向相同提交历史的分支](../jpg/两个指向相同提交历史的分支.png)。

git 通过一个名为 HEAD 的指针来告诉你当前所在的本地分支。



```
$ git log --oneline --decorate
```

可以使用以上命令查看各个分支当前所指的对象。



## 分支切换

```
  $ git checkout testing
```

可以使用 checkout 命令切换分支，此时 HEAD 指针就指向了 testing 分支了。



## 分支的新建与合并

### 新建分支

新建一个分支并同时切换到那个分支上，

```
$ git checkout -b iss53 
```

继续在 #53 问题上工作，并且做了一些提交，

```
$ vim index.html 
$ git commit -a -m 'added a new footer [issue 53]'
```

切换到你的线上分支(production branch)。

```
$ git checkout master
```

为这个紧急任务新建一个分支，并在其中修复它。

```
$ git checkout -b hotfix
$ vim index.html
$ git commit -a -m 'fixed the broken email address'
```

在测试通过之后，切换回线上分支，然后合并这个修补分支，最后将改动推送到线上分支。

```
$ git checkout master
$ git merge hotfix
```

关于这个紧急问题的解决方案发布之后，你准备回到被打断之前时的工作中。 然而，你应该先删除 hotfix 分
支，因为你已经不再需要它了 —— master 分支已经指向了同一个位置。 你可以使用带 -d 选项的 git
branch 命令来删除分支。

```
$ git branch -d hotfix
```

切换回你最初工作的分支上，继续工作。

```
$ git checkout iss53
$ vim index.html
$ git commit -a -m 'finished the new footer [issue 53]'
```



### 分支的合并

```
$ git checkout master
$ git merge iss53
```

这和你之前合并 hotfix 分支的时候看起来有一点不一样。 在这种情况下，你的开发历史从一个更早的地方开
始分叉开来(diverged)[继续在iss53分支上的工作](../jpg/继续在iss53分支上的工作.png)。

出现这种情况的时候，Git 会使用两个分支的末端所指的快照(C4 和 C5)以及这两个分支的工作祖先(C2)，做一个简单的[三方合并](../jpg/一次典型合并中所用到的三个快照.png)。和之前将分支指针向前推进所不同的是，Git 将此次三方合并的结果做了一个新的快照并且自动创建一个新的提 交指向它。 这个被称作一次合并提交，它的特别之处在于他有不止一个父提交。 

```
$ git branch -d iss53
```



### 遇到冲突时的分支合并

有时候合并操作不会如此顺利。 如果你在两个不同的分支中，对同一个文件的同一个部分进行了不同的修
改，Git 就没法干净的合并它们。

```
$ git merge iss53
```

此时 Git 做了合并，但是没有自动地创建一个新的合并提交。 Git 会暂停下来，等待你去解决合并产生的冲突。
你可以在合并冲突后的任意时刻使用 git status 命令来查看那些因包含合并冲突而处于未合并（unmerged）状态的文件：

```
$ git status
On branch master
You have unmerged paths.
(fix conflicts and run "git commit")
Unmerged paths:
(use "git add <file>..." to mark resolution)
both modified:      index.html
no changes added to commit (use "git add" and/or "git commit -a")
```

在你解决了所有文件里的冲突之后，对每个文件使用 git add 命令来将其标记为冲突已解决。 一旦暂存这
些原本有冲突的文件，Git 就会将它们标记为冲突已解决。

之后，你可以再次运行git status来确认所有的合并冲突都已被解决。

```
$ git status
On branch master
All conflicts fixed but you are still merging.
(use "git commit" to conclude merge)
Changes to be committed:
modified:   index.html
```

如果你对结果感到满意，并且确定之前有冲突的的文件都已经暂存了，这时你可以输入 git commit 来完成合
并提交。

```
$ git commit 
```



### 分支管理

得到当前所有分支的一个列表，

```
$ git branch
iss53
* master
testing
```

注意 master 分支前的 * 字符:它代表现在检出的那一个分支(也就是说，当前 HEAD 指针所指向的分支)。
这意味着如果在这时候提交，master 分支将会随着新的工作向前移动。 

如果需要查看每一个分支的最后一次提交，可以运行 git branch -v 命令:

```
$ git branch -v
iss53   93b412c fix javascript issue
* master  7a98805 Merge branch 'iss53'
testing 782fd34 add scott to the author list in the readmes
```

--merged 与 --no-merged 这两个有用的选项可以过滤这个列表中已经合并或尚未合并到当前分支的分支。
如果要查看哪些分支已经合并到当前分支，可以运行git branch --merged：

```
$ git branch --merged
iss53
* maste 
```

在这个列表中分支名字前没有 * 号的分支通常可以使用 git branch -d 删除掉;你已经将它们的工作整合到了另一个分支，所以并不会失去任何东西。

```
$ git branch --unmerged
testing
```

这里显示了其他分支。 因为它包含了还未合并的工作，尝试使用 git branch -d 命令删除它时会失败：

```
$ git branch -d testing
  error: The branch 'testing' is not fully merged.
  If you are sure you want to delete it, run 'git branch -D testing'.
```

如果真的想要删除分支并丢掉那些工作，如同帮助信息里所指出的，可以使用 -D 选项强制删除它。



### 分支开发工作流

#### 长期分支

- [渐进稳定分支的线性图](../jpg/渐进稳定分支的线性图.png)

- [渐进稳定分支的流水线(“silo”)视图](../jpg/渐进稳定分支的流水线视图.png)



#### 特性分支

考虑这样一个例子，你在 master 分支上工作到 C1，这时为了解决一个问题而新建 iss91 分支，在 iss91 分
支上工作到 C4，然而对于那个问题你又有了新的想法，于是你再新建一个 iss91v2 分支试图用另一种方法解决
那个问题，接着你回到 master 分支工作了一会儿，你又冒出了一个不太确定的想法，你便在 C10 的时候新建
一个 dumbidea 分支，并在上面做些实验。 你的提交历史看起来像下面这个样子

[拥有多个特性分支的提交历史](../jpg/拥有多个特性分支的提交历史.png)

现在，我们假设两件事情:你决定使用第二个方案来解决那个问题，即使用在 iss91v2分支中方案；另外，你将 dumbidea 分支拿给你的同事看过之后，结果发现这是个惊人之举。 这时你可以抛弃 iss91 分支(即丢弃 C5 和 C6 提交)，然后把另外两个分支合并入主干分支。 最终你的提交历史看起来像下面这个样子

[合并了dumbidea和iss91v2分支之后的提交历史](../jpg/合并了dumbidea和iss91v2分支之后的提交历史.png)

请牢记，当你做这么多操作的时候，这些分支全部都存于本地。当你新建和合并分支的时候，所有这一切都只发生在你本地的 Git 版本库中 —— 没有与服务器发生交互。



### 远程分支

显式地获得远程引用的完整列表，

```
$ git ls-remote (remote)
```

获得远程分支的更多信息，

```
$ git remote show (remote)
```

然而，一个更常见的获取远程分支信息的做法是利用远程跟踪分支。

远程跟踪分支是远程分支状态的引用。 它们是你不能移动的本地引用，当你做任何网络通信操作时，它们会自
动移动。 远程跟踪分支像是你上次连接到远程仓库时，那些分支所处状态的书签。

它们以**(remote)/(branch)** 形式命名。例如，如果你想要看你最后一次与远程仓库 origin 通信时 master 分支的状态，你可以查看 origin/master 分支。 你与同事合作解决一个问题并且他们推送了一个 iss53 分支，你可能有自己的本地 iss53 分支；但是在服务器上的分支会指向 origin/iss53 的提交。

这可能有一点儿难以理解，让我们来看一个例子。 假设你的网络里有一个在 git.ourcompany.com 的 Git 服务器。 如果你从这里克隆，Git 的 clone 命令会为你自动将其命名为 origin，拉取它的所有数据，创建一个指向它的 master 分支的指针，并且在本地将其命名为 **origin/master**。 Git 也会给你一个与 origin 的 master 分支在指向同一个地方的本地 **master** 分支，这样你就有工作的基础。

**Note**：“origin” 并无特殊含义，远程仓库名字 “origin” 与分支名字 “master” 一样，在 Git 中并没有任何特别的含义一样。 同时“master”是当你运行 **git init** 时**默认**的起始分支名字，原因仅仅是它的广泛使用， “origin” 是当你运行 git clone 时默认的**远程仓库**名字。 如果你运行 **git clone -o booyah**，那么你默认的远程分支名字将会是 **booyah/master**。 [克隆之后的服务器与本地仓库](../jpg/克隆之后的服务器与本地仓库.png)

如果你在本地的 master 分支做了一些工作，然而在同一时间，其他人推送提交到 git.ourcompany.com 并更新了它的 master 分支，那么你的提交历史将向不同的方向前进。 也许，只要你不与 origin 服务器连接，你的 origin/master 指针就不会移动。[本地与远程的工作可以分叉](../jpg/本地与远程的工作可以分叉.png)

如果要同步你的工作，运行 **git fetch origin**命令。 这个命令查找 “origin” 是哪一个服务器(在本例中，它是git.ourcompany.com)，从中抓取本地没有的数据，并且更新本地数据库，移动 origin/master指针指向新的、更新后的位置。[git fetch更新你的远程仓库引用](../jpg/gitfetch更新你的远程仓库引用.png)

为了演示有多个远程仓库与远程分支的情况，我们假定你有另一个内部 Git 服务器，仅用于你的 sprint 小组的开
发工作。 这个服务器位于 git.team1.ourcompany.com。 你可以运行 **git remote add** 命令添加一个新的远程仓库引用到当前的项目，这个命令我们会在 Git 基础 中详细说明。 将这个远程仓库命名为 teamone，将其
作为整个 URL 的缩写。[添加另一个远程仓库](../jpg/添加另一个远程仓库.png)

现在，可以运行 git fetch teamone 来抓取远程仓库 teamone 有而本地没有的数据。 因为那台服务器上现有的数据是 origin 服务器上的一个子集，所以 Git 并不会抓取数据而是会设置远程跟踪分支teamone/master 指向 teamone 的 master 分支。[远程跟踪分支 teamone/master](../jpg/远程跟踪分支.png)



#### 推送

当你想要公开分享一个分支时，需要将其推送到有写入权限的远程仓库上。 本地的分支并不会自动与远程仓库同步 - 你必须显式地推送想要分享的分支。 这样，你就可以把不愿意分享的内容放到私人分支上，而将需要和别
人协作的内容推送到公开分支。

如果希望和别人一起在名为 serverfix 的分支上工作，你可以像推送第一个分支那样推送它。运行 git push
(remote) (branch)：

```
$ git push (remote) (branch)
$ git push origin serverfix
```



**Note**：如何避免每次输入密码 ？如果你正在使用 HTTPS URL 来推送，Git 服务器会询问用户名与密码。 默认情况下它会在终端中提示服务器是否允许你进行推送。 如果不想在每一次推送时都输入用户名与密码，你可以设置一个 **credential cache**。 最简单的方式就是将其保存在内存中几分钟，可以简单地运行 **git config --global credential.helper cache** 来设置它。 

下一次其他协作者从服务器上抓取数据时，他们会在本地生成一个远程分支 origin/serverfix，指向服务器的 serverfix 分支的引用：

```
$ git fetch origin
  remote: Counting objects: 7, done.
  remote: Compressing objects: 100% (2/2), done.
  remote: Total 3 (delta 0), reused 3 (delta 0)
  Unpacking objects: 100% (3/3), done.
  From https://github.com/schacon/simplegit
   * [new branch]      serverfix    -> origin/serverfix
```

要特别注意的一点是当抓取到新的远程跟踪分支时，本地不会自动生成一份可编辑的副本(拷贝)。 换一句话说，这种情况下，不会有一个新的 serverfix 分支 - 只有一个不可以修改的 origin/serverfix 指针。

可以运行 **git merge origin/serverfix** 将这些工作合并到当前所在的分支。 如果想要在自己的 serverfix 分支上工作，可以将其建立在远程跟踪分支之上：

```
$ git checkout -b serverfix origin/serverfix
Branch serverfix set up to track remote branch serverfix from origin.
Switched to a new branch 'serverfix'
```



#### 跟踪分支

从一个远程跟踪分支检出一个本地分支会自动创建一个叫做 “跟踪分支”(有时候也叫做 “上游分支”)。跟踪分支是与远程分支有直接关系的本地分支。如果在一个跟踪分支上输入 **git pull**，Git 能自动地识别去哪个服务器上抓取、合并到哪个分支。

当克隆一个仓库时，它通常会自动地创建一个跟踪 origin/master 的 master 分支。 然而，如果你愿意的话可以设置其他的跟踪分支 - 其他远程仓库上的跟踪分支，或者不跟踪 master 分支。 最简单的就是之前看到的例子，运行 **git checkout -b [branch] [remotename]/[branch]**。 这是一个十分常用的操作所以 Git 提供了 --track 快捷方式：

```
$ git checkout --track origin/serverfix
Branch serverfix set up to track remote branch serverfix from origin.
Switched to a new branch 'serverfix'
```

如果想要将本地分支与远程分支设置为不同名字，你可以轻松地使用上一个命令增加一个不同名字的本地分支：

```
$ git checkout -b sf origin/serverfix
Branch sf set up to track remote branch serverfix from origin.
Switched to a new branch 'sf'
```

现在，本地分支 sf 会自动从 origin/serverfix 拉取。

设置已有的本地分支跟踪一个刚刚拉取下来的远程分支，或者想要修改正在跟踪的上游分支，你可以在任意时间 

使用 **-u** 或 **--set-upstream-to** 选项运行 **git branch** 来显式地设置。 

```
$ git branch -u origin/serverfix 
Branch serverfix set up to track remote branch serverfix from origin.
```

**NOTE**：上游快捷方式 - 当设置好跟踪分支后，可以通过 @{upstream} 或 @{u} 快捷方式来引用它。 所以在master 分支时并且它正在跟踪origin/master时，如果愿意的话可以使用**git merge @{u}**来取代 **git merge origin/master**。 

```
$ git branch -vv
iss53     7e424c3 [origin/iss53: ahead 2] forgot the brackets
master    1ae2a45 [origin/master] deploying index fix
* serverfix f8674d9 [teamone/server-fix-good: ahead 3, behind 1] this
should do it
testing   5ea463a trying something new
```

这里可以看到 iss53 分支正在跟踪 origin/iss53 并且 “ahead” 是 2，意味着**本地有两个提交还没有推送到服务器上**。 也能看到 master 分支正在跟踪 origin/master 分支并且是**最新的**。 接下来可以看到serverfix 分支正在跟踪 teamone 服务器上的 server-fix-good 分支并且领先 3 落后 1，意味着**服务器上有一次提交还没有合并入同时本地有三次提交还没有推送**。 最后看到 testing 分支并没有跟踪任何远程分支。

需要重点注意的一点是这些数字的值来自于你从每个服务器上最后一次抓取的数据。 这个命令并没有连接服务
器，它只会告诉你关于本地缓存的服务器数据。 如果想要统计最新的领先与落后数字，需要在运行此命令前抓
取所有的远程仓库。可以像这样做：

```
$ git fetch --all
$ git branch -vv
```



#### 拉取

当 **git fetch** 命令从服务器上抓取本地没有的数据时，它并不会修改工作目录中的内容。 它**只会获取数据**然后让你自己合并。 然而，有一个命令叫作 **git pull** 在大多数情况下它的含义是一个 **git fetch** 紧接着一个 **git merge** 命令。 如果有一个像之前章节中演示的设置好的跟踪分支，不管它是显式地设置还是通过 clone 或checkout命令为你创建的，git pull都会**查找当前分支所跟踪的服务器与分支**，从服务器上抓取数据然后尝试合并入那个远程支。 

由于 git pull 的魔法经常令人困惑所以通常**单独显式地使用 fetch 与 merge 命令会更好一些**。 



#### 删除远程分支

假设你已经通过远程分支做完所有的工作了 - 也就是说你和你的协作者已经完成了一个特性并且将其合并到了远程仓库的master分支(或任何其他稳定代码分支)。可以运行带有--delete选项的 git push 命令来删除一个远程分支。 如果想要从服务器上删除 serverfix 分支，运行下面的命令：

```
$ git push origin --delete serverfix
  To https://github.com/schacon/simplegit
   - [deleted]         serverfix
```

基本上这个命令做的只是从服务器上移除这个指针。 Git 服务器通常会保留数据一段时间直到垃圾回收运行，所
以如果不小心删除掉了，通常是很容易恢复的。



### 变基

在 Git 中整合来自不同分支的修改主要有两种方法:merge 以及 rebase。



#### 变基的基本操作

之前介绍过，整合分支最容易的方法是 merge 命令。 它会把两个分支的最新快照(C3 和 C4)以及二者最近的 

共同祖先(C2)进行三方合并，合并的结果是生成一个新的快照(并提交)。 [通过合并操作来整合分叉了的历史](../jpg/通过合并操作来整合分叉了的历史.png)

其实，还有一种方法:你可以提取在 C4 中引入的补丁和修改，然后在 C3 的基础上应用一次。 在 Git 中，这种
操作就叫做 变基。 你可以使用 rebase 命令将提交到某一分支上的所有修改都移至另一分支上，就好像“重新
播放”一样。

```
$ git checkout experiment
$ git rebase master
First, rewinding head to replay your work on top of it...
Applying: added staged command
```

 [将 C4 中的修改变基到 C3 上](../jpg/将C4中的修改变基到C3上.png)

现在回到 master 分支，进行一次快进合并[master 分支的快进合并](../jpg/master分支的快进合并.png)。

```
$ git checkout master
$ git merge experiment
```

这两种整合方法的最终结果没有任何区别，但是变基使得提交历史更加整洁。 你在查看一个经过变基的分支的历史记录时会发现，尽管实际的开发工作是并行的，但它们看上去就像是串行的一样，提交历史是一条直线没有分叉。

请注意，无论是通过变基，还是通过三方合并，整合的最终结果所指向的快照始终是一样的，只不过提交历史不 同罢了。 变基是将一系列提交按照原有次序依次应用到另一分支上，而合并是把最终结果合在一起。 



#### 更有趣的变基例子

假如，你创建了一个特性分支 server，为服务端添加了一些功能，提交了 C3 和 C4。 然后从 C3 上创建了特性分支 client，为客户端添加了一些功能，提交了 C8 和 C9。 最后，你回到 server 分支，又提交了 C10。[从一个特性分支里再分出一个特性分支的提交历史](../jpg/从一个特性分支里再分出一个特性分支的提交历史.png)

假设你希望将 client 中的修改合并到主分支并发布，但暂时并不想合并 server 中的修改，因为它们还需要经过更全面的测试。 这时，你就可以使用 git rebase 命令的 --onto 选项，选中在 client 分支里但不在 server 分支里的修改(即 C8 和 C9)，将它们在 master 分支上重放：

```
$ git rebase --onto master server client
```

以上命令的意思是：“取出 client 分支，找出处于 client 分支和 server 分支的共同祖先之后的修改，然后把它们在 master 分支上重放一遍”。 这理解起来有一点复杂，不过效果非常酷。[截取特性分支上的另一个特性分支然后变基到其他分支](../jpg/截取特性分支上的另一个特性分支然后变基到其他分支.png)

现在可以快进合并 master 分支了[ 快进合并master分支使之包含来自client分支的修改](../jpg/ 快进合并master分支使之包含来自client分支的修改.png)。

```
$ git checkout master
$ git merge client
```

接下来你决定将 server 分支中的修改也整合进来。 使用 **git rebase [basebranch] [topicbranch]** 命令可以直接将特性分支(即本例中的 server)变基到目标分支(即 master)上。这样做能省去你先切换到 server 分支，再对其执行变基命令的多个步骤。

```
$ git rebase master server
```

如图所示[将server中的修改变基到master上](../jpg/将server中的修改变基到master上.png)，server 中的代码被“续”到了 master 后面。



#### 变基的风险



#### 变基 vs. 合并

总的原则是，只对**尚未推送或分享给别人的本地修改执行变基操作清理历史**，**从不对已推送至别处的提交执行变基操作**，这样，你才能享受到两种方式带来的便利。

