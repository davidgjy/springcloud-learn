一. 授权码模式使用

操作步骤:

启动oauth2-server服务；
在浏览器访问该地址进行登录授权：http://localhost:9401/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all&state=normal


输入账号密码进行登录操作：
kg
123456

使用授权码请求该地址获取访问令牌：http://localhost:9401/oauth/token

