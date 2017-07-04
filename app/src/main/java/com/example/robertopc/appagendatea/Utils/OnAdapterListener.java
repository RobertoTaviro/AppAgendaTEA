package com.example.robertopc.appagendatea.Utils;

import com.example.robertopc.appagendatea.ElementosPersistentes.Pictograma;

import java.util.ArrayList;

/**
 * Created by RobertoPC on 04/07/2017.
 */

public interface OnAdapterListener {
    void onFamClicked(int position, ArrayList<Pictograma> pictos);
}
