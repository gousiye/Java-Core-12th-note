# git 简单学习笔记

## 用户相关
1. 需要设置用户，表明自己是谁。
2. `git config global user.name = "<name>"`来设置用名。用户邮箱类似。
 
 
## 提交相关
1. `git diff <file>`显示<font color = "red">工作目录，暂存区</font>的不同。
<br>`git diff --cached/--staged <file>`显示<font color = "red">暂存区，本地仓库最后一次提交（本地仓库中与当前分支相同的分支）</font>的不同
2. `git add <file>`将指定文件提交到暂存区。
3. `git reset <file>`将指定文件从暂存区移出。<font color = "red">本质上是将更改后的&lt;file&gt;从暂存区移出。暂存区中的&lt;file&gt;回滚本地仓库中最后一次提交的状态。</font>因此执行`reset`后，暂存区的&lt;file&gt;撤销了更改，但工作目录中的&lt;file&gt;保留着更改。
4. `git commit -m`将<font color = "red">暂存</font>区中的内容保存到<font color = "red">本地仓库</font>
5. `git pull <origin> <branch>`从分支上拉取最新的状态，并更新到本地仓库。<font color = "red">可能会有冲突问题。本质上执行了`git fetch`和`git merge <branch>`</font>。因此执行后本地仓库会进行更改。
6. `git push <origin> <branch>`更新分支
7. `git push -u origin <branch>` 在远程仓库建立分支，并通过本地仓库更新远程仓库中的该分支

## 分支相关
1. `git branch <branch>`创建一个分支
2. `git branch` 查看本地分支
3. `git branch -a`查看所有分支，包括HEAD
4. `git diff <branchA> <branchB> <file>`比较两个分支上的不同

## 合并相关  
1. `git merge <branch>`将指定分支合并到当前分支上，<font color = "red">会引发合并冲突。</font>


## 日志相关
1. `git checkout <file>`。会从暂存区的内容覆盖到工作目录上的&lt;file&gt;。<font color = "red">没有`git checkout <file> --cached/--staged`</font>。如果想恢复暂存区的内容，直接使用`git reset <file>`即可。