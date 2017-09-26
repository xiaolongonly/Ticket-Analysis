package cn.xiaolong.ticketsystem.adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.ticketsystem.R;
import cn.xiaolong.ticketsystem.bean.TicketRegular;

/**
 * <胆码选择适配器>
 *
 * @data: 2016/8/9 11:18
 * @version: V1.0
 */
public class CodeBaseAdapter extends RecyclerView.Adapter<CodeBaseAdapter.ViewHolder> {
    public static final int TYPE_NORMAL = 0; //第一种情况 只需要一个选择器，可供选择的大小为Size
    public static final int TYPE_REPEAT = 1;//第二种情况，每个号码一个选择器
    public static final int TYPE_NORMAL_WITH_SPECIAL = 2; //第三种情况  需要两个选择器  - - 这边只做了特别号码只有一种的情况。艾玛 不多做好看vle
    public static final int TYPE_REPEAT_WITH_SPECIAL = 3; //第四种情况 基础号码和特别号码 每个号码一个选择器
    private int type;
    private Context mContext;
    private boolean isRepeat; //是否重复
    private List<Pair<Integer, Integer>> codeDis; //号码分布  分为start的范围 和end的范围 first 1 second 25
    private List<String> specialCode; //这里的特别码用来做映射显示
    private List<Integer> codeSize; //需要多少个号码
    private List<CodeForChooseAdapter> mCodeForChooseAdapters;
    private List<List<String>> selectedList;

    public CodeBaseAdapter(Context context, TicketRegular ticketRegular, List<List<String>> numberBaseList) {
        mContext = context;
        isRepeat = ticketRegular.repeat;
        codeDis = getCodeDis(ticketRegular.codeDis);
        specialCode = getSpecialCode(ticketRegular.special);
        codeSize = getCodeSize(ticketRegular.regular);
        mCodeForChooseAdapters = new ArrayList<>();
        if (codeSize.size() < 1 && !isRepeat) {
            type = TYPE_NORMAL;
        }
        if (codeSize.size() < 1 && isRepeat) {
            type = TYPE_REPEAT;
        }
        if (codeSize.size() > 1 && !isRepeat) {
            type = TYPE_NORMAL_WITH_SPECIAL;
        }
        if (codeSize.size() > 1 && isRepeat) {
            type = TYPE_REPEAT_WITH_SPECIAL;
        }
        selectedList = numberBaseList == null ? new ArrayList<>() : numberBaseList;
    }


    private List<Integer> getCodeSize(String regular) {
        List<Integer> codeSize = new ArrayList<>();
        String[] regulars = regular.split("\\+");
        for (String size : regulars) {
            codeSize.add(Integer.valueOf(size));
        }
        return codeSize;
    }

    public List<List<String>> getSelectedList() {
        return selectedList;
    }

    private List<String> getSpecialCode(String special) {
        if (special == null) {
            return null;
        }
        List<String> specialCodeList = new ArrayList<>();
        String[] specialList = special.split("，");
        for (String str : specialList) {
            specialCodeList.add(str);
        }
        return specialCodeList;
    }

    private List<Pair<Integer, Integer>> getCodeDis(String codeDis) {
        List<Pair<Integer, Integer>> codeDisList = new ArrayList<>();
        String[] codeDisStrs = codeDis.split(",");
        for (String str : codeDisStrs) {
            String[] range = str.split("-");
            codeDisList.add(new Pair<Integer, Integer>(Integer.valueOf(range[0]), Integer.valueOf(range[1])));
        }
        return codeDisList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_number_base, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (type) {
            case TYPE_NORMAL:
                holder.setData(codeDis.get(0), codeSize.get(0), false, position, type);
                break;
            case TYPE_REPEAT:
                holder.setData(codeDis.get(0), codeSize.size(), false, position, type);
                break;
            case TYPE_NORMAL_WITH_SPECIAL:
                if (position < getItemCount() - 1) {
                    holder.setData(codeDis.get(0), codeSize.get(0), false, position, type);
                } else {
                    holder.setData(codeDis.size() > 1 ? codeDis.get(1) : codeDis.get(0), codeSize.get(1), true, position, type);
                }
                break;
            case TYPE_REPEAT_WITH_SPECIAL:
                if (position < codeSize.get(0)) {
                    holder.setData(codeDis.get(0), codeSize.size() - 1, false, position, type);
                } else {
                    holder.setData(codeDis.get(1), codeSize.size() - 1, true, position, type);
                }
                break;
            default:
                holder.setData(codeDis.get(0), codeSize.size(), false, position, type);

        }

    }

    @Override
    public int getItemCount() {
        switch (type) {
            case TYPE_NORMAL:
                return codeSize.size();
            case TYPE_REPEAT:
                return codeSize.get(0);
            case TYPE_NORMAL_WITH_SPECIAL:
                return codeSize.size();
            case TYPE_REPEAT_WITH_SPECIAL:
                return codeSize.get(0) + codeSize.get(1);
            default:
                return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvCodes;
        private TextView tvCodeColor;
        private TextView tvHint;

        public ViewHolder(View itemView) {
            super(itemView);
            rvCodes = (RecyclerView) itemView.findViewById(R.id.rvCodes);
            tvCodeColor = (TextView) itemView.findViewById(R.id.tvCodeColor);
            tvHint = (TextView) itemView.findViewById(R.id.tvHint);
        }

        public void setData(final Pair<Integer, Integer> data, int size, boolean isSpecial, int position, int type) {
            List<String> numberList;
            if (!isSpecial) {
                numberList = generateNumberList(data.first, data.second);
                tvCodeColor.setText("红球" + (position + 1));
            } else {
                tvCodeColor.setText("特别码");
                if (specialCode != null && specialCode.size() > 0) {
                    numberList = specialCode;
                } else {
                    numberList = generateNumberList(data.first, data.second);
                }
            }
            if (selectedList.size() <= position) {
                selectedList.add(new ArrayList<>());
            }
            tvHint.setText("可选0~" + size + "个码");
            rvCodes.setLayoutManager(new GridLayoutManager(mContext, 7));
            CodeForChooseAdapter codeForChooseAdapter;
            if (type == TYPE_NORMAL_WITH_SPECIAL && codeDis.size() <= 1) {
                codeForChooseAdapter = new CodeForChooseAdapter(mContext, numberList, isSpecial, size, position, selectedList);
                codeForChooseAdapter.setOnClickListener(v -> {
                    for (int i = 0; i < mCodeForChooseAdapters.size(); i++) {
                        mCodeForChooseAdapters.get(i).notifyDataSetChanged();
                    }
                });
            } else {
                codeForChooseAdapter = new CodeForChooseAdapter(mContext, numberList, isSpecial, size, selectedList.get(position));
            }

            mCodeForChooseAdapters.add(codeForChooseAdapter);
            rvCodes.setAdapter(codeForChooseAdapter);
        }

        private List<String> generateNumberList(Integer first, Integer second) {
            List<String> codeList = new ArrayList<>();
            for (int i = first; i <= second; i++) {
                codeList.add(i + "");
            }
            return codeList;
        }
    }
}
