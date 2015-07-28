if [ -e "../bin/embulk" ]; then
    echo "embulk already exists."
else
    curl --create-dirs -o ./bin/embulk -L "http://dl.embulk.org/embulk-latest.jar"
    chmod +x ./bin/embulk
    embulk example ./try1
fi
export PATH="`pwd`/../bin:$PATH"
