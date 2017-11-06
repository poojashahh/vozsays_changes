package com.vozsays.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vozsays.R;
import com.vozsays.activity.BaseFragment;
import com.vozsays.adapter.BehaviourAnswerAdapter;
import com.vozsays.databinding.FragBehaviourQuestionsBinding;
import com.vozsays.databinding.FragBinaryQuestionsBinding;
import com.vozsays.databinding.FragNoquestionsFoundBinding;
import com.vozsays.databinding.FragPollcompleteBinding;
import com.vozsays.databinding.FragQuestionBinding;
import com.vozsays.databinding.FragVisualchoiceQuestionBinding;
import com.vozsays.model.QuestionDataList;
import com.vozsays.model.QuestionOption;
import com.vozsays.model.QuestionPostDataModel;
import com.vozsays.retrofit.WebAPI;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by yudizsolutionspvt.ltd. on 16/09/17.
 */
public class QuestionFragment extends BaseFragment {
    private FragBinaryQuestionsBinding mBinaryBinding;
    private FragBehaviourQuestionsBinding mBehaviourBinding;
    private FragVisualchoiceQuestionBinding mVisualBinding;
    private FragQuestionBinding mQuestionBinding;
    private FragPollcompleteBinding mPollBinding;
    private FragNoquestionsFoundBinding mNoQuestionBinding;
    private Bundle b;
    Boolean IS_LAST=false;
    boolean isFourOption;
    private View v;
    private QuestionPostDataModel questionPostDataModel;
    private Boolean isYes;
    private int i = 0, pos, size;
    private List<QuestionDataList> questionDataList;
    private BehaviourAnswerAdapter behaviourAnswerAdapter;
    String iPollId, questionId, vType, yesOptionId, noOptionId, Vauthtoken;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mQuestionBinding = DataBindingUtil.inflate(inflater, R.layout.frag_question, container, false);
        b = this.getArguments();
        questionDataList = b.getParcelableArrayList("questionDataList");
        size = questionDataList.size();
        System.out.println("size"+size);
        if (questionDataList.size() != 0) {
            vType = questionDataList.get(i).getvType();
            layoutInflater(vType);
        }
        else if(questionDataList.size()==0){
            vType="no_more_questions";
            layoutInflater(vType);
        }
        return mQuestionBinding.getRoot();
    }

    private void layoutInflater(String vType) {
        if (vType.matches("Visual Choice")) {
            v = fillVisualLayout();

        } else if (vType.matches("Binary")) {
            v = fillBinaryLayout();
        }
        else if (vType.matches("last_question")){
            v=fillPollCompleteLayout();
        }
        else if (vType.matches("no_more_questions")){
            v=fillNoQuestionLayout();
        }
        else
         {
                v = fillBehaviourLayout();
         }
    }

    private View fillNoQuestionLayout() {
        mQuestionBinding.framelayout.removeAllViews();
        mNoQuestionBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.frag_noquestions_found, mQuestionBinding.framelayout, true);
       fillNoQuestionListener();
        return mNoQuestionBinding.getRoot();
    }

    private void fillNoQuestionListener() {

        mNoQuestionBinding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
    private View fillPollCompleteLayout() {
        mQuestionBinding.framelayout.removeAllViews();
            mPollBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.frag_pollcomplete, mQuestionBinding.framelayout, true);
            pollCompleteListener();
            return mPollBinding.getRoot();
    }
    private void pollCompleteListener() {
            mPollBinding.home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });

    }
    private View fillBinaryLayout() {
        mQuestionBinding.framelayout.removeAllViews();
        mBinaryBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.frag_binary_questions, mQuestionBinding.framelayout, true);
        pos = i;
        fillBinaryData();
        i++;
        binaryListener();
        return mBinaryBinding.getRoot();
    }

    private View fillBehaviourLayout() {
        mQuestionBinding.framelayout.removeAllViews();
        mBehaviourBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.frag_behaviour_questions, mQuestionBinding.framelayout, true);
        pos = i;
        fillBehaviourData();
        i++;
        behaviourlistener();
        return mBehaviourBinding.getRoot();
    }
    private View fillVisualLayout() {
        mQuestionBinding.framelayout.removeAllViews();
        mVisualBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.frag_visualchoice_question, mQuestionBinding.framelayout, true);
        pos = i;
        fillVisualData();
        i++;
        visuallistener();
        return mVisualBinding.getRoot();
    }
    private void fillBinaryData() {
        questionId = questionDataList.get(i).getiQuestionId();
        iPollId = questionDataList.get(i).getiPollId();
        yesOptionId = questionDataList.get(i).getOptions().get(0).getiOptionId();
        noOptionId = questionDataList.get(i).getOptions().get(1).getiOptionId();
        mBinaryBinding.binaryPb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        mBinaryBinding.binaryPb.setVisibility(View.VISIBLE);
        Glide.with(this).load(WebAPI.BASE_IMAGE_URL + questionDataList.get(i).getvImage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mBinaryBinding.binaryPb.setVisibility(View.VISIBLE);
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mBinaryBinding.binaryPb.setVisibility(View.GONE);
                return false;
            }
        }).into(mBinaryBinding.vImage);
        System.out.println("Image Url" + questionDataList.get(i).getvImage());
        mBinaryBinding.vCategory.setText(questionDataList.get(i).getvCategory());
        System.out.println(questionDataList.get(i).getvCategory());
        mBinaryBinding.vQuestion.setText(questionDataList.get(i).getvQuestion());
        System.out.println(questionDataList.get(i).getvQuestion());
    }

    private void binaryListener() {
        mBinaryBinding.optionYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYes = true;
                mBinaryBinding.optionYes.setBackgroundResource(R.drawable.pink_round_rect);
                mBinaryBinding.optionYes.setTextColor(getResources().getColor(android.R.color.white));
                mBinaryBinding.optionYes.setClickable(false);
                mBinaryBinding.optionNo.setVisibility(GONE);
                mBinaryBinding.answer.setVisibility(View.VISIBLE);
                callPostWebService(iPollId, yesOptionId);

            }
        });
        mBinaryBinding.optionNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYes = false;
                mBinaryBinding.optionNo.setBackgroundResource(R.drawable.pink_round_rect);
                mBinaryBinding.optionNo.setTextColor(getResources().getColor(android.R.color.white));
                mBinaryBinding.optionYes.setVisibility(GONE);
                mBinaryBinding.optionNo.setClickable(false);
                mBinaryBinding.answer.setVisibility(View.VISIBLE);
                callPostWebService(iPollId, noOptionId);


            }
        });
        mBinaryBinding.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("i" + i);
                if (i <size) {
                    vType = questionDataList.get(i).getvType();
                    layoutInflater(vType);

                } else {
                    vType="last_question";
                    IS_LAST=true;
                    layoutInflater(vType);
                }
            }
        });
    }
    private void fillBehaviourData() {
        mBehaviourBinding.behaviourPb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        questionId = questionDataList.get(pos).getiQuestionId();
        iPollId = questionDataList.get(pos).getiPollId();
        List<QuestionOption> questionOption = questionDataList.get(pos).getOptions();
        System.out.println("image url" + WebAPI.BASE_IMAGE_URL + questionDataList.get(pos).getvImage());
        mBehaviourBinding.behaviourPb.setVisibility(View.VISIBLE);
        Glide.with(this).load(WebAPI.BASE_IMAGE_URL + questionDataList.get(pos).getvImage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mBehaviourBinding.behaviourPb.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mBehaviourBinding.behaviourPb.setVisibility(View.GONE);
                return false;
            }
        }).into(mBehaviourBinding.vImage);

        mBehaviourBinding.vCategory.setText(questionDataList.get(pos).getvCategory());
        mBehaviourBinding.vQuestions.setText(questionDataList.get(pos).getvQuestion());
        mBehaviourBinding.optionlayout.setVisibility(View.VISIBLE);
        mBehaviourBinding.layoutFirstOptionBtn.setText(questionDataList.get(pos).getOptions().get(0).getvOption());
        mBehaviourBinding.layoutSecondOptionBtn.setText(questionDataList.get(pos).getOptions().get(1).getvOption());
        mBehaviourBinding.layoutThirdOptionBtn.setText(questionDataList.get(pos).getOptions().get(2).getvOption());
        mBehaviourBinding.layoutFourthOptionBtn.setVisibility(View.GONE);
        if (questionOption.size() == 4) {
             isFourOption = true;
            mBehaviourBinding.layoutFourthOptionBtn.setVisibility(View.VISIBLE);
            mBehaviourBinding.layoutFourthOptionBtn.setText(questionDataList.get(pos).getOptions().get(3).getvOption());
        }
        mBehaviourBinding.layoutFourthOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutFourthOptionBtn.setEllipsize(null);
                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutFourthOptionBtn.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mBehaviourBinding.layoutFourthOptionBtn.setLayoutParams(params);
                mBehaviourBinding.layoutFourthOptionBtn.setMaxLines(5);
            }
        });
        mBehaviourBinding.layoutFirstOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutFirstOptionBtn.setEllipsize(null);
                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutFirstOptionBtn.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mBehaviourBinding.layoutFirstOptionBtn.setLayoutParams(params);
                mBehaviourBinding.layoutFirstOptionBtn.setMaxLines(5);
            }
        });
        mBehaviourBinding.layoutSecondOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutSecondOptionBtn.setEllipsize(null);
                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutSecondOptionBtn.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mBehaviourBinding.layoutSecondOptionBtn.setLayoutParams(params);
                mBehaviourBinding.layoutSecondOptionBtn.setMaxLines(5);
            }
        });
        mBehaviourBinding.layoutThirdOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutThirdOptionBtn.setEllipsize(null);
                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutThirdOptionBtn.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mBehaviourBinding.layoutThirdOptionBtn.setLayoutParams(params);
                mBehaviourBinding.layoutThirdOptionBtn.setMaxLines(5);
            }
        });
    }
   private void behaviourlistener() {
        mBehaviourBinding.layoutFirstOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutFirstOptionBtn.setBackgroundResource(R.drawable.pink_round_rect);
                mBehaviourBinding.layoutFirstOptionBtn.setTextColor(getResources().getColor(android.R.color.white));
                mBehaviourBinding.layoutSecondOptionBtn.setClickable(false);
                mBehaviourBinding.layoutFirstOptionBtn.setClickable(false);
                mBehaviourBinding.layoutThirdOptionBtn.setClickable(false);
                mBehaviourBinding.layoutFourthOptionBtn.setClickable(false);
                callPostWebService(iPollId, questionDataList.get(pos).getOptions().get(0).getiOptionId());
//                mBehaviourBinding.layoutFirstOptionBtn.setEllipsize(null);
//                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutFirstOptionBtn.getLayoutParams();
//                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                mBehaviourBinding.layoutFirstOptionBtn.setLayoutParams(params);
//                mBehaviourBinding.layoutFirstOptionBtn.setMaxLines(5);
//                System.out.println(iPollId + "" + questionDataList.get(pos).getOptions().get(0).getiOptionId());

            }
        });
        mBehaviourBinding.layoutSecondOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutSecondOptionBtn.setBackgroundResource(R.drawable.pink_round_rect);
                mBehaviourBinding.layoutSecondOptionBtn.setTextColor(getResources().getColor(android.R.color.white));
                mBehaviourBinding.layoutFirstOptionBtn.setClickable(false);
                mBehaviourBinding.layoutSecondOptionBtn.setClickable(false);
                mBehaviourBinding.layoutThirdOptionBtn.setClickable(false);
                mBehaviourBinding.layoutFourthOptionBtn.setClickable(false);
//                mBehaviourBinding.layoutSecondOptionBtn.setEllipsize(null);
//                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutSecondOptionBtn.getLayoutParams();
//                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                mBehaviourBinding.layoutSecondOptionBtn.setLayoutParams(params);
//                mBehaviourBinding.layoutSecondOptionBtn.setMaxLines(5);
                callPostWebService(iPollId, questionDataList.get(pos).getOptions().get(1).getiOptionId());
            }
        });
        mBehaviourBinding.layoutThirdOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutThirdOptionBtn.setBackgroundResource(R.drawable.pink_round_rect);
                mBehaviourBinding.layoutThirdOptionBtn.setTextColor(getResources().getColor(android.R.color.white));
                mBehaviourBinding.layoutSecondOptionBtn.setClickable(false);
                mBehaviourBinding.layoutFirstOptionBtn.setClickable(false);
                mBehaviourBinding.layoutThirdOptionBtn.setClickable(false);
                mBehaviourBinding.layoutFourthOptionBtn.setClickable(false);
//                mBehaviourBinding.layoutThirdOptionBtn.setEllipsize(null);
//                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutThirdOptionBtn.getLayoutParams();
//                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                mBehaviourBinding.layoutThirdOptionBtn.setLayoutParams(params);
//                mBehaviourBinding.layoutThirdOptionBtn.setMaxLines(5);
                callPostWebService(iPollId, questionDataList.get(pos).getOptions().get(2).getiOptionId());

            }
        });
        mBehaviourBinding.layoutFourthOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehaviourBinding.layoutFourthOptionBtn.setBackgroundResource(R.drawable.pink_round_rect);
                mBehaviourBinding.layoutFourthOptionBtn.setTextColor(getResources().getColor(android.R.color.white));
                mBehaviourBinding.layoutSecondOptionBtn.setClickable(false);
                mBehaviourBinding.layoutThirdOptionBtn.setClickable(false);
                mBehaviourBinding.layoutFirstOptionBtn.setClickable(false);
                mBehaviourBinding.layoutFourthOptionBtn.setClickable(false);
                callPostWebService(iPollId, questionDataList.get(pos).getOptions().get(3).getiOptionId()); mBehaviourBinding.layoutFourthOptionBtn.setEllipsize(null);
//                ViewGroup.LayoutParams params =  mBehaviourBinding.layoutFourthOptionBtn.getLayoutParams();
//                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                mBehaviourBinding.layoutFourthOptionBtn.setLayoutParams(params);
//                mBehaviourBinding.layoutFourthOptionBtn.setMaxLines(5);


            }
        });
        mBehaviourBinding.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("i" + i);
                if (i < size) {
                    vType = questionDataList.get(i).getvType();
                    layoutInflater(vType);
                } else {
                    vType="last_question";
                   layoutInflater(vType);
                }
            }
        });
    }
    private void fillVisualData() {
        questionId = questionDataList.get(pos).getiQuestionId();
        iPollId = questionDataList.get(pos).getiPollId();
        mVisualBinding.visualPb1.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        mVisualBinding.visualPb2.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        mVisualBinding.visualPb1.setVisibility(View.VISIBLE);
        mVisualBinding.visualPb2.setVisibility(View.VISIBLE);
        Glide.with(this).load(WebAPI.BASE_IMAGE_URL + questionDataList.get(pos).getOptions().get(0).getvImage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
              mVisualBinding.visualPb1.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mVisualBinding.visualPb1.setVisibility(View.GONE);
                return false;
            }
        }).into(mVisualBinding.firstOptionIv);
        Glide.with(this).load(WebAPI.BASE_IMAGE_URL + questionDataList.get(pos).getOptions().get(1).getvImage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mVisualBinding.visualPb2.setVisibility(View.VISIBLE);
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mVisualBinding.visualPb2.setVisibility(View.GONE);
                return false;
            }
        }).into(mVisualBinding.secondOptionIv);
        System.out.println(questionDataList.get(pos).getvImage());
        mVisualBinding.vCategory.setText(questionDataList.get(pos).getvCategory());
        System.out.println(questionDataList.get(pos).getvCategory());
        mVisualBinding.vQuestion.setText(questionDataList.get(pos).getvQuestion());
        System.out.println(questionDataList.get(pos).getvQuestion());

    }

    private void visuallistener() {
        mVisualBinding.optionFirstRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVisualBinding.optionFirstBtn.setImageResource(R.drawable.logo);
                mVisualBinding.optionSecondRl.setClickable(false);
                mVisualBinding.optionFirstRl.setClickable(false);
                System.out.println("iPollId" + iPollId + "iOptionId" + questionDataList.get(pos).getOptions().get(0).getiOptionId());
                callPostWebService(iPollId, questionDataList.get(pos).getOptions().get(0).getiOptionId());

            }
        });
        mVisualBinding.optionSecondRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVisualBinding.optionSecondBtn.setImageResource(R.drawable.logo);
                mVisualBinding.optionFirstRl.setClickable(false);
                mVisualBinding.optionSecondRl.setClickable(false);
                System.out.println("iPollId" + iPollId + "iOptionId" + questionDataList.get(pos).getOptions().get(1).getiOptionId());
                callPostWebService(iPollId, questionDataList.get(pos).getOptions().get(1).getiOptionId());


            }
        });

        mVisualBinding.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("i" + i);
                if (i < size) {
                    vType = questionDataList.get(i).getvType();
                    layoutInflater(vType);

                } else {
                    vType="last_question";
                    layoutInflater(vType);
                }
            }
        });

    }
    private void callPostWebService(String iPollId, String iOptionId) {

        Vauthtoken = mPrefs.getAccessToekn();
        System.out.println(Vauthtoken + "" + iPollId + "" + iOptionId);
        if (validator.hasConnection(getActivity())) {
            apiTask.postQuestion(Vauthtoken, iPollId, iOptionId, callback);
        }
            else {
            validator.alert(getActivity(), getResources().getString(R.string.connection_fail_message));
        }
    }
    Callback<QuestionPostDataModel> callback = new Callback<QuestionPostDataModel>() {
        @Override
        public void onResponse(Call<QuestionPostDataModel> call, Response<QuestionPostDataModel> response) {
            dismissProgressDialog();//
            if (response != null && response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals("200")) {
                questionPostDataModel = response.body();
                String status = questionPostDataModel.getStatus();
                System.out.println(status);
                System.out.println("response" + response.toString());
                System.out.println(vType);
                if (vType.matches("Binary")) {
                    if (isYes) {
                        mBinaryBinding.answer.setText(questionPostDataModel.getData().getOptions().get(0).getPerc() + "% user agree with you");
                        System.out.println(questionPostDataModel.getData().getOptions().get(0).getPerc());
                        mBinaryBinding.rightArrow.setVisibility(View.VISIBLE);
                    } else {

                        mBinaryBinding.answer.setText(questionPostDataModel.getData().getOptions().get(1).getPerc() + " % user agree with you");
                            mBinaryBinding.rightArrow.setVisibility(View.VISIBLE);
                    }
                } else if (vType.matches("Behavior")) {
                    fillBehaviourAnsLayout();
                } else if (vType.matches("Visual Choice")) {
                    mVisualBinding.optionFirstBtn.setVisibility(GONE);
                    mVisualBinding.optionSecondBtn.setVisibility(GONE);
                    mVisualBinding.optionFirstText.setVisibility(View.VISIBLE);
                    mVisualBinding.optionSecongText.setVisibility(View.VISIBLE);
                    mVisualBinding.optionFirstText.setText(String.valueOf((int)Double.parseDouble(questionPostDataModel.getData().getOptions().get(0).getPerc()))+" %");
                    mVisualBinding.optionSecongText.setText(String.valueOf((int)Double.parseDouble(questionPostDataModel.getData().getOptions().get(1).getPerc()))+" %");
                    mVisualBinding.rightArrow.setVisibility(View.VISIBLE);

                }


            } else {
                if (response != null && response.body() != null && response.body().getStatus() != null && !response.body().getStatus().equals("200")
                        && response.body().getMessage() != null && response.body().getMessage() != null && !response.body().getMessage().equals("")) {
                    validator.alert(getActivity(), response.body().getMessage());
                    onFailure(null, null);
                }
            }
        }
        @Override
        public void onFailure(Call<QuestionPostDataModel> call, Throwable t) {
            Log.d("Tag", "throwable" + t.getMessage());
            dismissProgressDialog();
        }

    };
    private void fillBehaviourAnsLayout() {
        mBehaviourBinding.optionlayout.setVisibility(GONE);
        mBehaviourBinding.answerRecyclerview.setVisibility(View.VISIBLE);
        behaviourAnswerAdapter = new BehaviourAnswerAdapter(getApplicationContext(), questionPostDataModel.getData().getOptions());
        mBehaviourBinding.answerRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBehaviourBinding.answerRecyclerview.setAdapter(behaviourAnswerAdapter);
        mBehaviourBinding.rightArrow.setVisibility(View.VISIBLE);


    }
}


