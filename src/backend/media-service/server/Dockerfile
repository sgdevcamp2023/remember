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

# Expose port 5000
EXPOSE 5000
