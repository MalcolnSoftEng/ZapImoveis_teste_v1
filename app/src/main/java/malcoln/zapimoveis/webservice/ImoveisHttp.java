package malcoln.zapimoveis.webservice;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import malcoln.zapimoveis.ListWebServiceFragment;
import malcoln.zapimoveis.model.Filtro;
import malcoln.zapimoveis.model.Imoveis;

/**
 * Created by Malcoln on 28/11/2017.
 */

public class ImoveisHttp {
    public static final String BASE_URL ="http://demo4573903.mockable.io/imoveis";

    public static int QTDE_IMOVEIS = 0;

    public static int[] filtroPreco = new int[0];


    public static List<Imoveis> obterImoveisDoServidor(){
        List<Imoveis> listaDeImoveis = new ArrayList<>();

        try {
            JSONArray imoveisArray = getJsonArray();

            // looping through All Imoveis
            for (int i = 0; i < imoveisArray.length(); i++) {

                JSONObject jsonImoveis = imoveisArray.getJSONObject(i);

                Imoveis imoveis = new Imoveis();
                imoveis.setCodImovel(jsonImoveis.getString("CodImovel"));
                imoveis.setTipoImovel(jsonImoveis.getString("TipoImovel"));
                imoveis.setPrecoVenda(jsonImoveis.getString("PrecoVenda"));
                imoveis.setDormitorios(jsonImoveis.getString("Dormitorios"));
                imoveis.setSuites(jsonImoveis.getString("Suites"));
                imoveis.setVagas(jsonImoveis.getString("Vagas"));
                imoveis.setAreaUtil(jsonImoveis.getString("AreaUtil"));
                imoveis.setAreaTotal(jsonImoveis.getString("AreaTotal"));

                //Pega Objeto "Endereco" e valida quantidade de atributos do objeto, pois o primeiro item tem 6 atributos
                JSONObject jsonObjEndereco = jsonImoveis.getJSONObject("Endereco");
                if (jsonObjEndereco.length()<8){
                    imoveis.setEndereco(jsonObjEndereco.getString("Numero")+", Cep: "+jsonObjEndereco.getString("CEP")
                            +", Bairro: "+jsonObjEndereco.getString("Bairro")+", Cidade: "+jsonObjEndereco.getString("Cidade"));
                } else {
                    imoveis.setEndereco(jsonObjEndereco.getString("Logradouro")+","+jsonObjEndereco.getString("Numero")
                            +" - "+jsonObjEndereco.getString("Complemento")+", "+jsonObjEndereco.getString("Bairro")+", "+jsonObjEndereco.getString("Cidade"));
                }

                imoveis.setUrlImagem(jsonImoveis.getString("UrlImagem"));
                imoveis.setSubTipoOferta(jsonImoveis.getString("SubTipoOferta"));
                listaDeImoveis.add(imoveis);
                Log.e("SAIDA", "Lista: " + listaDeImoveis);
                QTDE_IMOVEIS = imoveisArray.length();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listaDeImoveis;
    }

    public static List<Imoveis> obterImoveisPorPreco(Filtro filtro){
        List<Imoveis> listaDeImoveis = new ArrayList<>();
        Imoveis imoveis = new Imoveis();


        String teste = "";
        try {
            JSONArray imoveisArray = getJsonArray();
            int acum = 0;
            JSONObject jsonImoveis = null;

            // looping through All Imoveis
            for (int i = 0; i < imoveisArray.length(); i++) {

                jsonImoveis = imoveisArray.getJSONObject(i);

                //filtroPreco[i] = Integer.parseInt(jsonImoveis.getString("PrecoVenda"));
                teste = jsonImoveis.getString("PrecoVenda");
                if (Integer.valueOf(teste)< filtro.getValor()) {
                    imoveis.setCodImovel(jsonImoveis.getString("CodImovel"));
                    imoveis.setTipoImovel(jsonImoveis.getString("TipoImovel"));
                    imoveis.setPrecoVenda(jsonImoveis.getString("PrecoVenda"));
                    imoveis.setDormitorios(jsonImoveis.getString("Dormitorios"));
                    imoveis.setSuites(jsonImoveis.getString("Suites"));
                    imoveis.setVagas(jsonImoveis.getString("Vagas"));
                    imoveis.setAreaUtil(jsonImoveis.getString("AreaUtil"));
                    imoveis.setAreaTotal(jsonImoveis.getString("AreaTotal"));

                    //Pega Objeto "Endereco" e valida quantidade de atributos do objeto, pois o primeiro item tem 6 atributos
                    JSONObject jsonObjEndereco = jsonImoveis.getJSONObject("Endereco");
                    if (jsonObjEndereco.length() < 8) {
                        imoveis.setEndereco(jsonObjEndereco.getString("Numero") + ", Cep: " + jsonObjEndereco.getString("CEP")
                                + ", Bairro: " + jsonObjEndereco.getString("Bairro") + ", Cidade: " + jsonObjEndereco.getString("Cidade"));
                    } else {
                        imoveis.setEndereco(jsonObjEndereco.getString("Logradouro") + "," + jsonObjEndereco.getString("Numero")
                                + " - " + jsonObjEndereco.getString("Complemento") + ", " + jsonObjEndereco.getString("Bairro") + ", " + jsonObjEndereco.getString("Cidade"));
                    }

                    imoveis.setUrlImagem(jsonImoveis.getString("UrlImagem"));
                    imoveis.setSubTipoOferta(jsonImoveis.getString("SubTipoOferta"));

                    listaDeImoveis.add(imoveis);
                    QTDE_IMOVEIS = listaDeImoveis.size();
                }
            }


                Log.e("SAIDA", "Lista: " + listaDeImoveis);
            Log.e("TESTE FILTRO"," valor :" + filtro.getValor());
            
        } catch (Exception e){
            e.printStackTrace();
        }

        return listaDeImoveis;
    }

    private static JSONArray getJsonArray() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(5, TimeUnit.SECONDS);
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();
        Response response = client.newCall(request).execute();
        String jsonStr = response.body().string();

        Log.e("SAIDA", "Resposta da URL: " + jsonStr);

        JSONObject jsonObj = new JSONObject(jsonStr);

        // Getting JSON Array node
        return jsonObj.getJSONArray("Imoveis");
    }

    public static int retornaQTDE(){

        return QTDE_IMOVEIS ;
    }

}
