# MAKEFILE for PROJECT 1

SRC_BASE_DIR = .
DFA_FILES    = $(SRC_BASE_DIR)/DFA.java $(SRC_BASE_DIR)/DFABuilder.java $(SRC_BASE_DIR)/State.java
FUNC_FILES   = $(SRC_BASE_DIR)/Homomorphic.java $(SRC_BASE_DIR)/Simulator.java  
MAIN_FILES   = $(SRC_BASE_DIR)/Main.java $(SRC_BASE_DIR)/Messenger.java

proj1.jar : $(DFA_FILES) $(FUNC_FILES) $(MAIN_FILES)
	javac *.java
	jar cvfe proj1.jar Main *.class

clean : 
	rm -fr proj1.jar
