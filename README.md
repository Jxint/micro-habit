hi ,江！
这个目前就是主仓库，需要弄到本地就需要先配置ssh（你应该弄过了）
我们已经把项目放到了 GitHub 上。以后你需要做这几件事：拉取我的最新代码 → 修改文件 → 提交 → 推送回远程仓库。
一、准备工作（仅第一次）
1. 安装 Git
      访问 https://git-scm.com/downloads ，下载 Windows 版本，安装时一路默认即可。
2. 克隆仓库到本地（把云端项目下载到你的电脑）
      打开任意文件夹，在地址栏输入 cmd 回车，然后执行：

   git clone git@github.com:Jxint/micro-habit.git

   如果提示输入密码，请使用我给你的 Personal Access Token（不是 GitHub 登录密码）。
   完成后，你会看到一个 micro-habit 文件夹，项目就在里面。
3. 以后每次操作前，先进入项目目录

   cd micro-habit

二、日常协作流程（按顺序执行）

1， 拉取最新代码（每次开始工作前必须做）

git pull

这个命令会把我在远程仓库里最新的修改下载到你的电脑上。
如果不做这一步，你直接推送可能会产生冲突。

2️，修改或添加文件

用你的编辑器正常修改代码、添加新文件。

3️，查看你改动了哪些文件

git status


会显示红色的文件（未暂存）和绿色的文件（已暂存）。

4️，将修改添加到暂存区

· 添加单个文件：
  ```cmd
  git add 文件名
  ```
· 添加所有修改（不包括新文件夹）：
  ```cmd
  git add .
  ```
· 添加所有修改（包括新文件夹）：
  ```cmd
  git add -A
  ```

5️ 提交到本地仓库

```cmd
git commit -m "简要说明你做了什么修改"
```

例如：git commit -m "修复登录页面的样式"

6️ 推送到远程仓库

```cmd
git push
```

这样你的修改就会同步到 GitHub 上，我就能看到了。

三、常见问题与解决办法

问题现象 可能原因 解决办法
git pull 报错“fatal: not a git repository” 你没有在项目目录内 确保先 cd micro-habit
git push 报错“failed to push some refs” 远程有新的提交，你没有先拉取 先执行 git pull，再 git push
提示“CONFLICT”（冲突） 你和我修改了同一个文件的同一行 手动打开冲突文件，删除 <<<<<<<、=======、>>>>>>> 这些标记，保留你想要的代码，然后 git add 文件名 + git commit -m "解决冲突" + git push
不知道当前分支名 查看当前分支 git branch （带 * 的就是当前分支）
忘记 git add 直接 commit 了 没有暂存任何文件 会提示“nothing to commit”，先 git add 再 commit

四、注意事项

· 每次推送前一定先 git pull，养成习惯。
· 不要上传大文件（视频、压缩包、node_modules 等），可以用 .gitignore 忽略它们。
· 提交信息尽量写清楚，方便以后回顾。
· 如果遇到解决不了的错误，把完整的报错信息截图发给我，我来帮你。

五、快速命令速查表

操作 命令
克隆仓库 git clone 仓库地址
拉取更新 git pull
查看状态 git status
添加所有修改 git add .
提交 git commit -m "说明"
推送 git push
查看提交历史 git log --oneline
