package Schemas;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class SchemaConvector {
    @Schema(description = "Путь до папки с входными файлами", example = "src\\test\\resources")
    private final String Path_Initial_Data;
    @Schema(description = "Путь до папки с результирующими файлами", example = "src\\test\\resources")
    private final String Path_Result;
    @Schema(description = "Количество обработанных файлов")
    private int Quantity_Processed_Files;
    @Schema(description = "Параметр")
    private String Parameter;
    public SchemaConvector(String Path_InitialData, String Path_Result,int Quantity_Processed_Files, String Parameter) {
        this.Path_Initial_Data = Path_InitialData;
        this.Path_Result = Path_Result;
        this.Quantity_Processed_Files = Quantity_Processed_Files;
        this.Parameter = Parameter;
    }
    public static SchemaConvector of(String Path_InitialData, String Path_Result,int Quantity_Processed_Files, String Parameter) {
        return new SchemaConvector(Path_InitialData, Path_Result,Quantity_Processed_Files, Parameter);
    }
    @Schema(hidden=true)
    public void setParameter(String parameter) {
        Parameter = parameter;
    }
    @Schema(hidden=true)
    public String getParameter() {
        return Parameter;
    }
    @Schema(hidden=true)
    public String getPath_Result() {
        return Path_Result;
    }
    @Schema(hidden=true)
    public String getPath_Initial_Data() {
        return Path_Initial_Data;
    }
    @Schema(hidden=true)
    public int getQuantity_Processed_Files() {
        return Quantity_Processed_Files;
    }
}