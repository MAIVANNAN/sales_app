package sales_app.com.sales_app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sales_app.com.sales_app.Activities.Instock;
import sales_app.com.sales_app.inStock;
import sales_app.com.sales_app.soldItems;
import sales_app.com.sales_app.unAvailableGoods;

public class PagerAdapter extends FragmentStatePagerAdapter {


    int mNoOfTabs;
     public  PagerAdapter(FragmentManager fm, int NumberOfTabs)
     {
         super(fm);
         this.mNoOfTabs = NumberOfTabs;

     }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                inStock inStock = new inStock();
                        return inStock;

            case 1:
                soldItems soldItems = new soldItems();
                return soldItems;

            case 2:
                unAvailableGoods unAvailableGoods = new unAvailableGoods();
                return unAvailableGoods;


            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
