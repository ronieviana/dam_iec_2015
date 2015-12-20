package br.puc.ronie.dbadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 58400 on 24/11/2015.
 */
public class ContatoAdapter extends ArrayAdapter<ContatoModel> {

    public ContatoAdapter(Context context, int resource, List<ContatoModel> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContatoModel contato = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contato_adapter, parent, false);
        }

        TextView tvId = (TextView) convertView.findViewById(R.id.textId);
        TextView tvNome = (TextView) convertView.findViewById(R.id.textNome);
        TextView tvEMail = (TextView) convertView.findViewById(R.id.textEMail);

        tvId.setText(String.valueOf(contato.getId()));
        tvNome.setText(contato.getNome());
        tvEMail.setText(contato.getEmail());
        return convertView;
    }


}
