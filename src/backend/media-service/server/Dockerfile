# Base image
FROM node:18

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
COPY package*.json ./

# use npm to install dependencies
RUN npm install

# Bundle app source
COPY . .

# Nesjs build
RUN npm run build

EXPOSE 5000
# mediasoup worker가 사용할 RTP 포트 범위를 expose합니다.
EXPOSE 2000-2020