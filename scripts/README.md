# remember - scripts

### Script 문서입니다.

- `./docker` : 각 서비스에 대한 도커 파일 설정이며, Jekins 서버에 존재합니다.
- `./jenkins` : jeknis의 설정 파일이 존재합니다.
- `./sql` : DB의 기본 스키마 형태가 존재합니다.
- `./nginx` : NGINX의 설정 파일들이 존재합니다.
- `./efk` : EFK의 설정 파일들이 존재합니다.


### 세부 설명
- `NGINX` -> 총 4개의 path가 존재하며, WebSocket의 통로인 STOMP, SocketIO, HTTP 통신 경로인 /api가 존재합니다. /api의 경우 Smile-Gateway로 연결되어 있습니다.