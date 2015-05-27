public class Estadisticas{
    private Double ocio;
    private Double ejecucion;
    private Double espera;
    
    public Estadisticas(){
        this.ocio=0.0;
        this.ejecucion=0.0;
        this.espera=0.0;
    }

    public Double getOcio(){
        return this.ocio;
    }

    public Double getEjecucion(){
        return this.ejecucion;
    }

    public Double getEspera(){
        return this.espera;
    }

    public void setOcio(Double ocio){
        this.ocio=ocio;
    }

    public void setEjecucion(Double ejecucion){
        this.ejecucion = ejecucion;
    }

    public void setEspera(Double espera){
        this.espera=espera;
    }


}