# Estudando gRPC
### Para compilar o protobuf
``protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/addressbook.proto
``

### Compilar o Java
``javac -cp ".:protobuf-java-3.8.0.jar:" -d out src/com/example/tutorial/*.java
``