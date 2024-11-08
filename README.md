 Pour compiler et executer :
 - Se placer dans le dossier MCTS
 - Compiler les fichiers java en executant la commande : javac -d classes -cp lib/pddl4j-4.0.0.jar src/fr/uga/pddl4j/examples/asp/*.java src/fr/uga/pddl4j/examples/asp/Node.java 
- Le planner MCTS peut être lancé manuellement avec n'importe quel domaine et problème associé en executant la commande : java -cp "classes;lib/pddl4j-4.0.0.jar" fr.uga.pddl4j.examples.asp.MCTS <Domain.pddl> <Problem.pddl>
( un exemple de domaine et de problème étant ".\src\test\resources\adl\domain.pddl" pour le domaine et ".\src\test\resources\benchmarks\pddl\ipc1998\logistics\adl\p01.pddl" )
- les types de problèmes sont disponibles dans le dossier src/test/

 