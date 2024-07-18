- Integrantes
  - Ivan Capeli Navas, 802286
  - Matheus Yuiti Moriy Miata, 802097
  - Vítor Milanez, 804319

---

Implementação de um gerador de código para a Linguagem Algorítmica (LA) desenvolvida pelo professor Jander.

- Executar com o corretor automático:

```Shell
# java -jar caminhoCompilador caminhoJar gcc caminhoTemp caminhoCasosTeste
# "RAs" Trabalho
java -jar ../corretor.jar "java -jar /home/mymm/comp/compT5/target/jander-gerador-1.0-SNAPSHOT-jar-with-dependencies.jar" gcc ./temp /home/mymm/comp/casos-de-teste "802286, 802097, 804319" t5
```

- Executar manualmente:

```Shell
# java -jar CaminhoJarWirhDependencies caminhoEntrada caminhoSaida
java -jar ./target/jander-gerador-1.0-SNAPSHOT-jar-with-dependencies.jar ./Entrada/1.declaracao_leitura_impressao_inteiro.alg 1.c
```