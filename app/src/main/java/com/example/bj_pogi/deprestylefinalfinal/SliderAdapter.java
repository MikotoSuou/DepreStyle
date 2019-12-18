package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ravi on 2/7/18.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;
    }

    public int[] slideImages = {
            R.drawable.ic_smile,
            R.drawable.ic_bounce,
            R.drawable.ic_play_outside,
            R.drawable.ic_walk,
            R.drawable.ic_yoga,
            R.drawable.ic_run,
            R.drawable.ic_muscle
    };

    public String[] slideHeadings ={
            "Daily physical exercises" + "\n" + "\n" + "\n" +"Lift up your mood by doing these activities!",
            "Bounce!",
            "Go Play Outside",
            "Get Your Walk On",
            "Become a Yogi",
            "Set Off That Runner's High",
            "Build Your Muscles"
    };

    public String[] slideDescriptions ={
            "",
            "Want something super-simple to break you out of a funk — at least temporarily? Be bouncey" + "\n" + "\n" +
            "“You don’t need to jump, but bend your knees and bounce as quickly as you can for a few minutes,” says Bennett. “This is an easy way to oxygenate your brain and get some endorphins flowing”",

            "If you enjoy being outdoors, even simple activities such as gardening, throwing a ball around with your kids, or washing your car may do you some good. That’s because a healthy dose of sunlight has been shown to boost mood, likely due to the fact that sunshine stimulates our serotonin levels (drops in serotonin during the darker, colder months have been linked to seasonal affective disorder, or SAD)" + "\n" + "\n" +
            "“Just moving your body inside or out is exercise,” says Shoshana Bennett, PhD, a clinical psychologist and author of Postpartum Depression for Dummies. “Choose whatever works for you, depending on your functioning level, energy, and preferences”",

            "Simply putting one foot in front of the other may be the trick to feeling better — that’s because walking is an aerobic exercise that’s suited for almost everyone. All it takes is a pair of comfortable, supportive shoes, and you’re ready to go" + "\n" + "\n" +
            "“Practical wisdom suggests that doing something is better than doing nothing in terms of physical activity,” says Muzina. If depression has made you sedentary, start off slowly and gradually increase time and distance”",

            "Ohm — in a study of 65 women with depression and anxiety, the 34 women who took a yoga class twice a week for two months showed a significant decrease in depression and anxiety symptoms, compared to the 31 women who were not in the class" + "\n" + "\n" +
            "“Eastern traditions such as yoga have a wonderful antidepressant effect in that they improve flexibility; involve mindfulness, which breaks up repetitive negative thoughts; increase strength; make you aware of your breathing; improve balance; and contain a meditative component,” says Norman E. Rosenthal, MD, a clinical professor of psychiatry at the Georgetown University School of Medicine in Washington, D.C”",

            "Ever heard of runner’s high? “The most tangible example of exercise stimulating certain brain chemicals is the runner’s high that many athletes report experiencing once crossing a certain threshold of exertion while running,” explains Muzina. That euphoria is due to the release of endorphins in the brain in response to the sustained physical activity" + "\n" + "\n" +
            "“Endorphins are our body’s natural morphine and, when released by special glands in our brains, they can produce a sense of well-being or joy and also decrease pain levels.”",

            "Boost your strength, boost your happiness? A recent study of 45 stroke survivors with depression found that a 10-week strength training program helped reduced symptoms of depression (among numerous other benefits)" + "\n" + "\n" +
            "“Strength training is about mastery and control,” says Leslie Seppinni, PhD, a clinical psychologist and family therapist in Beverly Hills. “It requires full attention and concentration. More importantly, people can see the results, the outline of the muscles forming, from dedication and training”" + "\n" + "\n" +
                    "Just be sure to start slowly and use the assistance of a personal trainer if needed"
    };


    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ConstraintLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.iv_image_icon);
        TextView slideHeading = (TextView) view.findViewById(R.id.tv_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.tv_description);

        slideImageView.setImageResource(slideImages[position]);
        slideHeading.setText(slideHeadings[position]);
        slideDescription.setText(slideDescriptions[position]);

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);  //todo: RelativeLayout??
    }
}
