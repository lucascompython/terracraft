all:
	@cargo build
	@javac -h . TerraCraft.java
	@java -Djava.library.path=./target/debug TerraCraft
