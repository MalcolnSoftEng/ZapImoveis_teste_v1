package malcoln.zapimoveis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import malcoln.zapimoveis.controller.ListaAdapter;
import malcoln.zapimoveis.model.Filtro;
import malcoln.zapimoveis.model.Imoveis;
import malcoln.zapimoveis.webservice.ImoveisHttp;

/**
 * Created by Malcoln on 28/11/2017.
 */

public class ListWebServiceFragment extends Fragment implements ListaAdapter.AoClicarNoImovelListener{

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txtQTDE)
    TextView txtQTDE;

    @BindView(R.id.btnFiltro)
    Button btnFiltro;

    public String qtde;

    Unbinder unbinder;

    List<Imoveis> mImoveis;
    ImoveisDownloadTask mTask;

    public boolean filtro = false;

    public Filtro mFiltro;

    private ListaAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.recyler_lista_imoveis,container,false);

        ButterKnife.bind(this,view);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTask = new ImoveisDownloadTask();
                mTask.execute();
            }
        });

        mRecyclerView.setTag("web");
        mRecyclerView.setHasFixedSize(true);
        //if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //} else {
       // mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        // }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        btnFiltro.setOnClickListener(new View.OnClickListener() {

            private int progressChangedValue;

            @Override
            public void onClick(View view) {
                
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                final View msgView = layoutInflater.inflate(R.layout.busca_tela_layout,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(msgView);
                alertDialogBuilder.setTitle("Filtre por preço");
                final SeekBar seekBar = msgView.findViewById(R.id.seekBar);
                final TextView txtVInserido = msgView.findViewById(R.id.txtValorInserido);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            txtVInserido.setText("R$"+String.valueOf(seekBar.getProgress()));
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) { }
                });

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(getActivity(), "Valor :" + seekBar.getProgress(),
                                        Toast.LENGTH_SHORT).show();
                                mFiltro = new Filtro(seekBar.getProgress());
                                mFiltro.setValor(seekBar.getProgress());
                                filtro = true;
                                new ImoveisDownloadTask().execute();

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //chamar unbind da view
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mImoveis == null){
            if (mTask == null){
                mTask = new ImoveisDownloadTask();
                mTask.execute();
            } else if (mTask.getStatus() == AsyncTask.Status.RUNNING){
                exibirProgresso();
            }
        } else {
            atualizarLista();
        }
    }

    @Override
    public void aoClicarNoItem(View view, int position, Imoveis imoveis) {
        Intent it = new Intent(getActivity(), ImovelDetalhesActivity.class);
        it.putExtra(ImovelDetalhesActivity.EXTRA_ITEM, imoveis);
        startActivity(it);
    }

    private void atualizarLista() {
        adapter = new ListaAdapter(getActivity(), mImoveis);
        adapter.setAoClicarNoItemListener(this);
        mRecyclerView.setAdapter(adapter);
        atualizarQtdeItens();
    }

    private void atualizarQtdeItens() {
        qtde = String.valueOf(ImoveisHttp.retornaQTDE());
        txtQTDE.setText(" "+qtde+ " de " + qtde + " Imoveis Encontrados");
    }


    public void exibirProgresso(){
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
            }
        });
    }


    private class ImoveisDownloadTask extends AsyncTask<Void, Void, List<Imoveis>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute(); //verificar conexão e criar alert caso não tenha
            exibirProgresso();
        }
        @Override
        protected List<Imoveis> doInBackground(Void... voids) {
            List valor = null;
            if (!filtro){
                valor = ImoveisHttp.obterImoveisDoServidor();

            }else{
                valor = ImoveisHttp.obterImoveisPorPreco(mFiltro);
                filtro=false;
            }
            return valor;
        }

        protected void onPostExecute(List<Imoveis> imoveis) {
            super.onPostExecute(imoveis);
            mSwipe.setRefreshing(false);
            if (imoveis != null){
                mImoveis= imoveis;
                atualizarLista();
            }
        }
    }


}
