FROM selenium/standalone-chrome:latest

# Copy your project files into the container
COPY . .

# Set working directory to your project directory
WORKDIR /app

# Run your tests
CMD ["mvn", "test"]
