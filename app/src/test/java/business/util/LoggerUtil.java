package business.util;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class LoggerUtil
	{
		private static org.apache.log4j.Logger	logger				= org.apache.log4j.Logger.getLogger(LoggerUtil.class);
		
		private static ObjectMapper				objectMapper		= new ObjectMapper();
		private static ObjectWriter				objectWriter		= objectMapper.writerWithDefaultPrettyPrinter();
		private static boolean					isPreviousRequired	= false;
		
		public static void log(String testCaseName, Object object)
			{
				try
					{
						if ( object == null || testCaseName == null || testCaseName.trim().length() == 0 ) { return; }
						String fileName = "output" + File.separator + testCaseName + ".json";
						if ( isPreviousRequired )
							{
								File temp = new File(fileName);
								if ( temp.exists() )
									{
										FileUtils.moveFile(temp, new File("output" + File.separator + "old" + File.separator + testCaseName + System.nanoTime() + ".json"));
									}
							}
						File file = new File(fileName);
						FileWriter fileWriter = new FileWriter(file);
						fileWriter.write(objectWriter.writeValueAsString(object));
						fileWriter.flush();
						fileWriter.close();
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
			
		public static void log(String folderName, String name, Object object)
			{
				try
					{
						if ( object == null || name == null || name.trim().length() == 0 ) { return; }
						String filePath = "output" + File.separator + folderName;
						File folder = new File(filePath);
						if ( folder.exists() == false )
							{
								FileUtils.forceMkdir(folder);
							}
						String fileName = filePath + File.separator + name + ".json";
						if ( isPreviousRequired )
							{
								File temp = new File(fileName);
								if ( temp.exists() )
									{
										FileUtils.moveFile(temp, new File("output" + File.separator + "old" + File.separator + name + System.nanoTime() + ".json"));
									}
							}
						File file = new File(fileName);
						FileWriter fileWriter = new FileWriter(file);
						fileWriter.write(objectWriter.writeValueAsString(object));
						fileWriter.flush();
						fileWriter.close();
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
			}
	}
