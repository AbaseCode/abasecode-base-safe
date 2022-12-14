# About

AbaseCode open source project is a set of open source collection . Including the base package , toolkit , security
package , token package , payment package , excel package and so on.

Open source project components to do out of the box, to facilitate more developers to save duplication of work, more
focused on business logic code writing.

I am Jon, a developer who focuses on learning and spreading technical knowledge. I hope these toolkits can help you, and
welcome any friends to join this open source project.

project homepage : https://abasecode.com

project github : https://github.com/abasecode

Jon's blog : https://jon.wiki

e-mail: ijonso123@gmail.com

# About abasecode-base-safe

A http interface encryption and decryption component.
Receive encrypted http request data and decrypt and submit business processing.
Return the encrypted data to the http client.

# Quick Start

## Step 1: setting the pom.xml add dependency

``` xml
<dependency>
    <groupId>com.abasecode.opencode</groupId>
    <artifactId>abasecode-base-safe</artifactId>
    <version>1.0.3</version>
</dependency>
```

## Step 2: setting application.yaml

``` yaml
app:
  safe:
    has-rsa: true|false. Boolean. If it is true, it means that the configured IV, KEY, and SECRET have been encrypted with asymmetric public key. The pri-key asymmetric private key string must be configured.
    iv: String format. Symmetrically encrypted IV. The length is 16 bits. example: "1234567890123456"
    key: String format. The key of symmetric encryption. The length is 32 bits. example: "01234567891234560123456789123456"
    secret: String format. The secret of symmetric encryption. The length is 32 bits. example: "1829b4abbba0794301a075fc2283d2ba"    
    pri-key: String format. If has-rsa is true, this value must be configured. It represents the private key string of asymmetric encryption used by the system. example: "MIIEvQIBADANBgkqh...."
```

## Step 3: add @EnableCodeSafe in SpringBoot Application

Like this:

```java
@SpringBootApplication
@EnableCodeSafe
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
```

Of course, you can also use

```java
@SpringBootApplication
@ComponentScan({"com.abasecode.opencode.base.safe","com..."})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
```

The @EnableCodeSafe annotation is recommended. Here's the answer: who ask me why it doesn't work.

## Step 4: Use @Encrypt annotation before the method that needs encryption

example:

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

It maybe returns

```json
{
    "code": 0,
    "message": "OK",
    "sign": "46E5FC4DF302BF74F66666C3DBC05810CCBB486B",
    "data": "97XVcP9tJ7Vh7glQlc4R/SpSDKI6gWKcZHnmqfrSkpKUQmkTe4fYtw83qKgz5tTGkeMyaC9F4UmzGPqDVxa0U2A5h/jRn1eUnYvHFARky6ZWY99VFBJ3WDHYQBBZTK9P11C4a1J+Zw=="
}
```

## Step 5: Use @Decrypt annotation in the parameter to decrypt.

example:

```
    @PostMapping(value = "/user")
    public CodeResult setUser(@RequestBody @Decrypt User u){
        return CodeResult.ok("OK",u);
    }
```

the json data:

```json
{
    "sign": "46E5FC4DF302BF74F66666C3DBC05810CCBB486B",
    "data": "97XVcP9tJ7Vh7glQlc4R/SpSDKI6gWKcZHnmqfrSkpKUQmkTe4fYtw83qKgz5tTGkeMyaC9F4UmzGPqDVxa0U2A5h/jRn1eUnYvHFARky6ZWY99VFBJ3WDHYQBBZTK9P11C4a1J+Zw=="
}
```

And then return:

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

## Step 6 : No more step. Enjoy it.

## Default Key

### public key

```
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr7EzZwdUqO5K/PZpS7dhVUT6DBslpzKcAgrV8GmPnksjbH4QzM5OrMtePvmLPxLZGxc4PClSo0+xLVsc9C9ycQs71xB/8TcDcWugoWMITJAiqbB1mF0zo0aruklJLJZSyjSLbWqZXE7dcW2f86FQ+CduBpZWojTW2WVRSP7urxGR8pc2Rxm21bvGW0i9BgbUVgrvRZxrvXtL9pUDPFZw96eB85ZY8p7/Dbz6yK+JENRn2ePIsLhfD2ut/YlR0SfPq5NPKAmtOvH2EwawU6XQ24i1dpuNRPTdQUET9c78LHcfGlWvY2ccdgudecHR+2C7hN5owsp5d6FnVW4EV5qNGQIDAQAB
```

### private key

you can find it in the code.
Warning: For data security, please do not use the default key pair.

## AES example

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