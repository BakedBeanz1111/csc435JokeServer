echo "Simple build and launch script"

echo "Clean Build Environment"
rm *.class

echo "Building src code"
javac *.java

echo "Starting Server"
start "Joke Server" cmd /c "Java JokeServer"

echo "Starting Client"
start "Joke Client 1" cmd /c "Java JokeClient"

echo "Starting Admin Client"
start "Admin Client" cmd /c "Java JokeClientAdmin"