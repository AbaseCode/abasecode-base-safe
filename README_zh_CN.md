# 关于AbaseCode

AbaseCode OpenCode是一套开源合集。包括基础包、工具包、安全包、token包、支付包、excel包等。

开源项目的组件做到开箱即用，方便更多的开发者节省重复的工作，更专注于业务逻辑代码编写。

我是Jon，一名全栈开发者，专注于学习和传播技术知识。希望这些工具包能够帮上你，欢迎有的朋友加入这个开源项目。

project homepage : https://abasecode.com

project github : https://github.com/abasecode

Jon's blog : https://jon.wiki

e-mail: ijonso123@gmail.com

# 关于 abasecode-base-safe

一个http接口加密和解密组件。 接收加密的http请求数据并解密，提交给业务逻辑处理，之后将加密后的数据返回给http客户端。

# 开始使用

## Step 1: 配置 pom.xml

``` xml
<dependency>
    <groupId>com.abasecode.opencode</groupId>
    <artifactId>abasecode-base-safe</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Step 2: 配置 application.yaml

``` yaml
app:
  safe:
    has-rsa: true|false. 布尔值。如果为真，意味着配置的IV、KEY和SECRET已经用非对称公钥进行了加密。必须配置pri-key非对称私钥字符串。
    iv：字符串格式。对称加密的IV。长度为16位。例如。"1234567890123456"
    key: 字符串格式。对称加密的密钥。长度为32位。例如。"01234567891234560123456789123456"
    secret: 字符串格式。对称加密的秘密。长度为32位。例如。"1829b4abbba0794301a075fc2283d2ba"    
    pri-key：字符串格式。如果has-rsa为真，必须配置此值。它代表系统使用的非对称加密的私钥字符串，例如。"MIIEvQIBADANBgkqh...."
```

## Step 3: 添加 @EnableCodeSafe 注解

如下:

```java
@SpringBootApplication
@EnableCodeSafe
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
```

当然，也可以使用如下：

```java
@SpringBootApplication
@ComponentScan({"com.abasecode.opencode.base.safe","com..."})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
```

推荐使用 @EnableCodeSafe 注解。

## Step 4: 使用 @Encrypt 注解加密

示例代码:

``` java
    @GetMapping(value = "/user")
    @Encrypt
    public CodeResult getUser(){
        User u = new User()
                .setEmail("ijonso123@gmail.com")
                .setId(1)
                .setName("JonSo");
        return CodeResult.ok("OK", u);
    }
```

它将返回：

```json
{
    "code": 0,
    "message": "OK",
    "sign": "46E5FC4DF302BF74F66666C3DBC05810CCBB486B",
    "data": "97XVcP9tJ7Vh7glQlc4R/SpSDKI6gWKcZHnmqfrSkpKUQmkTe4fYtw83qKgz5tTGkeMyaC9F4UmzGPqDVxa0U2A5h/jRn1eUnYvHFARky6ZWY99VFBJ3WDHYQBBZTK9P11C4a1J+Zw=="
}
```

## Step 5: 使用 @Decrypt 注解解密

示例代码:

``` java
    @PostMapping(value = "/user")
    public CodeResult setUser(@RequestBody @Decrypt User u){
        return CodeResult.ok("OK",u);
    }
```

json 数据:

```json
{
    "sign": "46E5FC4DF302BF74F66666C3DBC05810CCBB486B",
    "data": "97XVcP9tJ7Vh7glQlc4R/SpSDKI6gWKcZHnmqfrSkpKUQmkTe4fYtw83qKgz5tTGkeMyaC9F4UmzGPqDVxa0U2A5h/jRn1eUnYvHFARky6ZWY99VFBJ3WDHYQBBZTK9P11C4a1J+Zw=="
}
```

返回结果:

```json
{
    "data": {
        "id": 1,
        "name": "JonSo",
        "email": "ijonso123@gmail.com"
    },
    "code": 0,
    "message": "OK",
    "status": "SUCCESS"
}
```

## 默认 Key

### public key

```
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr7EzZwdUqO5K/PZpS7dhVUT6DBslpzKcAgrV8GmPnksjbH4QzM5OrMtePvmLPxLZGxc4PClSo0+xLVsc9C9ycQs71xB/8TcDcWugoWMITJAiqbB1mF0zo0aruklJLJZSyjSLbWqZXE7dcW2f86FQ+CduBpZWojTW2WVRSP7urxGR8pc2Rxm21bvGW0i9BgbUVgrvRZxrvXtL9pUDPFZw96eB85ZY8p7/Dbz6yK+JENRn2ePIsLhfD2ut/YlR0SfPq5NPKAmtOvH2EwawU6XQ24i1dpuNRPTdQUET9c78LHcfGlWvY2ccdgudecHR+2C7hN5owsp5d6FnVW4EV5qNGQIDAQAB
```

### private key

请看代码。
注意：请勿使用默认秘钥。

## AES 示例

```java
    public static void main(String[]args)throws Exception{
        String txt="Hello world!";
        String key="0123456780123456";
        String iv="012345678012";
        String add="this is additional";
        String encrypt=encrypt(txt,key,iv,add);
        System.out.println(encrypt);
        String decrypt=decrypt(encrypt,key,iv,add);
        System.out.println(decrypt);
    }
```