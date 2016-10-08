package org.jmt;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;
import static org.apache.maven.plugins.annotations.ResolutionScope.TEST;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jmt.util.jaxb.JmtType;

@Mojo(name = "generate", defaultPhase = GENERATE_SOURCES, requiresDependencyResolution = TEST)
public class Plugin extends AbstractMojo {

	@Parameter
	public JmtType jmt;

	private File sourceFolder = null;
	private File targetFolder = null;

	private FilenameFilter recursiveFilter;

	@SuppressWarnings("unused")
	public void processFilesRecursive(File pFile) {
		for (File file : pFile.listFiles(recursiveFilter)) {
			if (file.isDirectory()) {
				processFilesRecursive(file);
			} else {
				FileWriter writer = null;
				try {
					Parser parser = new Parser();
					Path path = Paths.get(file.getPath());
					String code = new String(Files.readAllBytes(path));
					String fileName = path.getFileName().toString();
					fileName = fileName.substring(0, fileName.indexOf("."));
					String packagePath = file.getPath().replace(sourceFolder.getPath(), "");
					packagePath = packagePath.substring(0, packagePath.indexOf(path.getFileName().toString()));
					String output = parser.parse(fileName, code);
					File targetFile = new File(targetFolder.getPath() + packagePath + fileName + ".java");
					if (targetFile.exists() == false) {
						targetFile.getParentFile().mkdirs();
					}
					writer = new FileWriter(targetFile);
					if (packagePath.length() > 1) {
						String packageStr = packagePath.substring(1, packagePath.length() - 1);
						if (File.separator.equals("\\")) {
							packageStr = packageStr.replaceAll("\\\\", ".");
						} else {
							packageStr = packageStr.replaceAll(File.separator, ".");
						}
						output = "package " + packageStr + ";\n\n" + output;
					}
					System.out.println(targetFile);
					writer.write(output);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("JMT started");
		sourceFolder = new File(jmt.getSource());
		targetFolder = new File(jmt.getTarget());
		final String filterText = jmt.getFilter() == null ? ".jmt." : null;
		recursiveFilter = new FilenameFilter(){
            public boolean accept( File dir, String name ) { 
                return new File(dir.getPath().concat(File.separator).concat(name)).isDirectory() || name.indexOf(filterText) != -1;
            }
         };
		processFilesRecursive(sourceFolder);
		System.out.println("JMT ended");
	}

	public static void main(String... strings) throws MojoExecutionException, MojoFailureException {
		Plugin plugin = new Plugin();
		plugin.jmt = new JmtType();
		plugin.jmt.setSource("C:/Users/codar.victor/Documents/GitHub/codegen-maven/src/main/resources/jmt");
		plugin.jmt.setTarget("C:/Users/codar.victor/Documents/GitHub/codegen-maven/target/generated-sources/jmt");
		plugin.execute();
	}

}
