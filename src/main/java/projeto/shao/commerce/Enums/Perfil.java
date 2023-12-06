package projeto.shao.commerce.Enums;

public enum Perfil {
    
    ADMIN("Administrador"),
    COMERCIANTE("Comerciante"),
    CLIENTE("Cliente");

    private String perfil;

    private Perfil(String perfil){
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
