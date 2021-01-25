
#############
### build ###
#############

# base image
FROM node:12.20.0 as build

# install chrome for protractor tests
# RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
# RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
# RUN apt-get update && apt-get install -yq google-chrome-stable


# Manual instalation of  node / npm 

# RUN apt-get update && apt-get install -y curl
# RUN curl -sL https://deb.nodesource.com/setup_8.x | bash -
# RUN apt-get update && apt-get install -y nodejs


# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /app/package.json

RUN npm install yarn

# RUN npm update
RUN yarn install
RUN yarn global add @angular/cli@8.3.27
# RUN yarn audit fix --force

# RUN npm install -g yarn


# add app
COPY . /app

# run tests
# RUN ng test --watch=false
# RUN ng e2e --port 4202

# Arguments
#ARG BACKEND_API_URL
#ENV BACKEND_API_URL=${BACKEND_API_URL}

# generate build
RUN ng build --prod --output-path=dist


############
### prod ###
############

# base image
# FROM nginx:1.16.0-alpine

# base image
FROM nginx:1.16.0-alpine

# copy artifact build from the 'build environment'
COPY --from=build /app/dist /usr/share/nginx/html
                                                  

# expose port 80
# EXPOSE 80

# run nginx
CMD ["nginx", "-g", "daemon off;"]