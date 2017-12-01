package malcoln.zapimoveis.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Malcoln on 28/11/2017.
 */

public class Imoveis implements Serializable{


    public String CodImovel;
    public String TipoImovel;
    public String Endereco;
    public String PrecoVenda;
    public String Dormitorios;
    public String Suites;
    public String Vagas;
    public String AreaUtil;
    public String AreaTotal;
    public String DataAtualizacao;
    public String Cliente;
    public String UrlImagem;
    public String SubTipoOferta;
    public String SubtipoImovel;


    public String getCodImovel() {
        return CodImovel;
    }

    public void setCodImovel(String codImovel) {
        CodImovel = codImovel;
    }

    public String getTipoImovel() {
        return TipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        TipoImovel = tipoImovel;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getPrecoVenda() {
        return PrecoVenda;
    }

    public void setPrecoVenda(String precoVenda) {
        PrecoVenda = precoVenda;
    }

    public String getDormitorios() {
        return Dormitorios;
    }

    public void setDormitorios(String dormitorios) {
        Dormitorios = dormitorios;
    }

    public String getSuites() {
        return Suites;
    }

    public void setSuites(String suites) {
        Suites = suites;
    }

    public String getVagas() {
        return Vagas;
    }

    public void setVagas(String vagas) {
        Vagas = vagas;
    }

    public String getAreaUtil() {
        return AreaUtil;
    }

    public void setAreaUtil(String areaUtil) {
        AreaUtil = areaUtil;
    }

    public String getAreaTotal() {
        return AreaTotal;
    }

    public void setAreaTotal(String areaTotal) {
        AreaTotal = areaTotal;
    }

    public String getDataAtualizacao() {
        return DataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        DataAtualizacao = dataAtualizacao;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getUrlImagem() {
        return UrlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        UrlImagem = urlImagem;
    }

    public String getSubTipoOferta() {
        return SubTipoOferta;
    }

    public void setSubTipoOferta(String subTipoOferta) {
        SubTipoOferta = subTipoOferta;
    }

    public String getSubtipoImovel() {
        return SubtipoImovel;
    }

    public void setSubtipoImovel(String subtipoImovel) {
        SubtipoImovel = subtipoImovel;
    }

    public Imoveis() {
    }

    @Override
    public String toString() {
        return TipoImovel;
    }
}
