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

public class mSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public mSliderAdapter(Context context){
        this.context=context;
    }

    public int[] slideImages = {
            R.drawable.ic_brain,
            R.drawable.ic_experience,
            R.drawable.ic_connect_socially,
            R.drawable.ic_puzzle,
            R.drawable.ic_hobbies,
            R.drawable.ic_sleep
    };

    public String[] slideHeadings ={
            "Daily mental exercises" + "\n" + "\n" + "\n" +"  Why is it important to keep your brain active?",
            "Experience something new",
            "Connect in the real world",
            "Do puzzles",
            "Indulge in your curiosities and hobbies",
            "Getting enough sleep"
    };

    public String[] slideDescriptions ={
            "• Can help prevent or lessen depression" + "\n" + "• May slow down or lessen the impact of dementia" + "\n" + "• Shows signs of improving cognitive functioning",

            "When confronted with a new environment, action or challenge, your brain analyses and adapts to it" + "\n" + "\n" + "This doesn’t have to be an explosive, life-changing event – it can be as simple as:" + "\n" +
                    "• Writing with your opposite hand" + "\n" + "• Walking a different way to work or home" + "\n" + "• Visiting a new place" + "\n" + "• Listening to different styles of music" + "\n" + "• Meeting new people",

            "Face-to-face social interactions force our brains to think and act on the spot. Conversations, discussions and exploring ideas challenges your brain to accept new concepts and entertain abstract thoughts" + "\n" + "\n" + "Changing your communication from leisurely (oh, I’ll get back to them when I feel like) to an immediate face-to-face style can be tricky, but there are ways to help you get into the swing of things:" + "\n" +
            "• Have a conversation with your recipient before or after sending them an email" + "\n" + "• Interact with people more personally – instead of leaving a message, make a call. If you usually make calls, organize to meet face-to-face" ,

            "Puzzles are like weights for the mind – deciphering abstract concepts, performing math problems, and working to find answers helps your brain develop new neural pathways" + "\n" + "\n" + "There are puzzle types to suit almost anyone:" + "\n" +
                    "• Chess" + "\n" + "• Some video games" + "\n" + "• Card games" + "\n" + "• Sudoku" + "\n" + "• Crosswords" + "\n" + "• Word jumbles",

            "There’s a universe of interests out there, each with its own history and community. Rekindle old passions or start one fresh by typing one of your interests into Google!" + "\n" + "\n" + "You could start with:" + "\n" +
                    "• Sports" + "\n" + "• Painting" + "\n" + "• Instruments" + "\n" + "• History" + "\n" + "• Books, blogs and poetry" + "\n" + "• Writing",

            "When you’re asleep, you’re no longer bothering your mind with thoughts of excel spreadsheets. Your brain still powers along during this quiet time, and you can help it by getting the best sleep possible" + "\n" + "\n" +
                    "To get a better quality of sleep, look to improve your sleep hygiene by:" + "\n" + "• Avoiding stimulants before bed" + "\n" + "• Associate your bed with sleeping"


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
        View view = layoutInflater.inflate(R.layout.mslide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.miv_image_icon);
        TextView slideHeading = (TextView) view.findViewById(R.id.mtv_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.mtv_description);

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
