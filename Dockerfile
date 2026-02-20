# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Install Chrome and ChromeDriver dependencies
RUN apt-get update && apt-get install -y \
    curl \
    gnupg \
    unzip \
    wget \
    && curl https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && wget -q -O /tmp/chromedriver.zip https://googlechromelabs.github.io/chrome-for-testing/ \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Copy Maven wrapper and project files
COPY . /app

# Ensure Maven wrapper has execution permissions
RUN chmod +x ./mvnw

# Display Java version
RUN java -version

# Display Chrome version
RUN google-chrome --version

# Run Maven tests
# Uses headless mode for Docker environment
ENV HEADLESS=true

CMD ["./mvnw", "clean", "test", "-DargLine=-Dheadless=true"]
