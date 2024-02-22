# Smile Gateway
### 1. [아키텍처](#아키텍처)
### 2. [동작 방식](#동작-방식)
### 3. [Config]()
### 4. [DownStream]()
### 5. [UpStream]()
### 6. [Common]()
### 7. [문제 해결 과정]()

## 로고
<div style="position: relative; text-align: center;">
  <p align="left">
  <img src="../../../resources/Smilegateway.png" alt="Image" style="width: 30%;">
  </p>
  <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); color: white; font-size: 21px;">
    Smilegate 로고</br> + </br>게이트웨이</br> = 수제작
  </div>
</div>

## 아키텍처
### Request 흐름도
![image](../../../resources/smile-gateway/전체%20흐름.png)

### Filter Chain 흐름도 | 폴더 구조
![image](../../../resources/smile-gateway/FilterChain%20흐름도.png)  ![image](../../../resources/smile-gateway/gateway-core.png)


## 동작 방식

## Config
### 기본 골자
![image](../../../resources/smile-gateway/Config형태.png)
### Config 파일 설정 ([예제]())
- LogPath
    
    LogPath: `string`
    
- Listeners
    - name : `string`
    - Protocol : `string`
    - ThreadCount : `int`
    - RequestTimeout : `int`
    - DisallowHeaders : `List<string>`
    - Address
        - Address : `string`
        - Port : `int`
    - RouteConfig
        - Clusters : `List<string>`
    - CustomFilters : `List<CustomFilterConfig>`
        - Name : `string`
        - Path : `string`
    - Authorization
        - Type : `string`
        - LoginPath : `string`
        - LogoutPath : `string`
        - JwtValidator
            - ValidAudience : `string`
            - ValidIssuer : `string`
            - SecretKey : `string`
            - AccessTokenValidityInSecond : `int`
            - RefreshTokenValidityInSecond : `int`
- Clusters
    - Name : `string`
    - Protocol : `string`
    - Prefix : `string`
    - Authorization : `bool`
    - ConnectTimeout : `int?`
    - RequestTimeout : `int?`
    - Address
        - Address : `string`
        - Port : `int`
    - CustomFilters : `List<CustomFilterConfig>`

## DownStream

