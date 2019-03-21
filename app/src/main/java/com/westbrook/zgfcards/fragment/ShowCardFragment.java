package com.westbrook.zgfcards.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.westbrook.zgfcards.CardInfo;
import com.westbrook.zgfcards.CardInfoManagerAgent;
import com.westbrook.zgfcards.R;

import org.w3c.dom.Text;

import java.util.Hashtable;

public class ShowCardFragment extends BaseFragment {


    private CardInfo cardInfo;

    private TextView foodName;

    private TextView numText;

    private ImageView qrImage;

    private Button confirmButton;

    private boolean inMainFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.show_card_fragment, null);

        foodName = (TextView) contentView.findViewById(R.id.foodNameText);

        foodName.setText(cardInfo.foodName+" "+cardInfo.leftNum+"次");

        numText = (TextView) contentView.findViewById(R.id.num_text);

        numText.setText(cardInfo.cardId+"");

        qrImage =(ImageView) contentView.findViewById(R.id.qrImage);

        confirmButton = (Button) contentView.findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardInfoManagerAgent.getCardInfoManagetAgent(getContext()).changeCardInfo(cardInfo);
                listener.showCradList();
            }
        });


        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        showQrCode();
    }

    public void setCard(CardInfo card) {

        this.cardInfo = card;

    }

    private void showQrCode(){
        try {
            Drawable drawable = new BitmapDrawable(createQcore(cardInfo.cardId+"",960));
            qrImage.setBackgroundDrawable(drawable);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    private Bitmap createQcore(String url, int widthAndHeight) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();

        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 字符编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        hints.put(EncodeHintType.MARGIN, "2");
        BitMatrix matrix = new MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
