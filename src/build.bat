echo "Simple build and launch script"

echo "Building src code"
javac *.java

echo "Starting Server"
start "Joke Server" cmd /c "Java JokeServer"

echo "Starting Client"
start "Joke Client" cmd /c "Java JokeClient"
