# For the gradle wrapper
ifeq ($(OS),Windows_NT)
	shell := cmd /c 
else
	shell := sh 
endif

all : @run

print: 
	@echo "OS: $(OS) shell: $(shell)"

rust_run:
	@echo "Rust run"
	@cd TerraCraft && cargo build & cd ..
jvm_run:
	@echo "JVM run"
	@cd TerraCraftJVM && $(shell) gradlew run && cd ..
net_run:
	@echo ".NET run"
	@cd TerraCraftNET && dotnet run && cd ..	


rust_build:
	@echo "Rust build"
	@cd TerraCraft && cargo build --release & cd ..
jvm_build:
	@echo "JVM build"
	@cd TerraCraftJVM && $(shell) gradlew build && cd ..
net_build:
	@echo ".NET build"
	@cd TerraCraftNET && dotnet build -c Release && cd ..


rust_clean:
	@echo "Rust clean"
	@cd TerraCraft && cargo clean & cd ..
jvm_clean:
	@echo "JVM clean"
	@cd TerraCraftJVM && $(shell) gradlew clean && cd ..
net_clean:
	@echo ".NET clean"
	@cd TerraCraftNET && dotnet clean && dotnet clean -c Release && cd ..


@run : rust_run jvm_run net_run
run: @run

@build : rust_build jvm_build net_build
build: @build

@clean : rust_clean jvm_clean net_clean
clean: @clean
