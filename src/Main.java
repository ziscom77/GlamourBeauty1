//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        DocumentoDao dao = new DocumentoDao();

        // INSERT
        dao.save(new DocumentoIPO(
                0,                       // id se autogenera
                "Prospecto",
                "Borrador preliminar",
                "Pendiente",
                null, null));

        // SELECT *
        dao.findAll().forEach(System.out::println);
    }
}