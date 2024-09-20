import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileAndRun {
    public static void main(String[] args) {
        try {
            Process compileProcess = new ProcessBuilder("javac", "src/main/java/CurrentDateTime.java").start();

            compileProcess.waitFor();

            if (compileProcess.exitValue() == 0) {
                System.out.println("Компиляция прошла успешно!");

                // Запуск скомпилированного файла
                Process runProcess = new ProcessBuilder("java", "-cp", "src/main/java", "CurrentDateTime").start();


                // Чтение вывода выполнения программы
                BufferedReader outputReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                String outputLine;
                while ((outputLine = outputReader.readLine()) != null) {
                    System.out.println(outputLine);
                }

                // Чтение ошибок выполнения программы
                BufferedReader runErrorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                String runErrorLine;
                while ((runErrorLine = runErrorReader.readLine()) != null) {
                    System.out.println("Ошибка выполнения: " + runErrorLine);
                }

                runProcess.waitFor();
            } else {
                System.out.println("Ошибка при компиляции.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}