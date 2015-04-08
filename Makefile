# MAKEFILE for PROJECT 1

SRC_BASE_DIR = src/csce355_project1
DFA_FILES    = $(SRC_BASE_DIR)/dfa/DFA.java $(SRC_BASE_DIR)/dfa/DFABuilder.java $(SRC_BASE_DIR)/dfa/State.java
FUNC_FILES   = $(SRC_BASE_DIR)/functions/Analyzer.java $(SRC_BASE_DIR)/functions/ComplementIntersect.java $(SRC_BASE_DIR)/functions/Homomorphic.java $(SRC_BASE_DIR)/functions/Minimizer.java $(SRC_BASE_DIR)/functions/Simulator.java $(SRC_BASE_DIR)/functions/TextSearch.java  
MAIN_FILES   = $(SRC_BASE_DIR)/Main.java $(SRC_BASE_DIR)/Messenger.java

CLS_BASE_DIR = ./csce355_project1
CLS_DFA_FILES  = $(CLS_BASE_DIR)/dfa/DFA.class $(CLS_BASE_DIR)/dfa/DFABuilder.class $(CLS_BASE_DIR)/dfa/State.class
CLS_FUNC_FILES = $(CLS_BASE_DIR)/functions/Analyzer.class $(CLS_BASE_DIR)/functions/ComplementIntersect.class $(CLS_BASE_DIR)/functions/Homomorphic.class $(CLS_BASE_DIR)/functions/Minimizer.class $(CLS_BASE_DIR)/functions/Simulator.class $(CLS_BASE_DIR)/functions/TextSearch.class
CLS_MAIN_FILES = $(CLS_BASE_DIR)/Main.class $(CLS_BASE_DIR)/Messenger.class

proj1.jar : $(DFA_FILES) $(FUNC_FILES) $(MAIN_FILES)
	rm -fr proj1.jar bin
	mkdir bin
	javac @compilerConfig/options @compilerConfig/classes
	jar cvfe proj1.jar csce355_project1.Main -C compiled/ .

clean : 
	rm -fr proj1.jar bin
