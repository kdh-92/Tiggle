import { useState } from "react";
import { Edit3 } from "react-feather";

import CTAButton from "@/components/atoms/CTAButton/CTAButton";
import Feeds from "@/components/molecules/Feed/Feeds";
import Navigation from "@/components/molecules/Navigation";
import { ContentStyle } from "@/styles/components/ContentStyle";
import { Tx } from "@/types";

const feedData = [
  {
    id: 1,
    txType: Tx.Outcome,
    amount: 50000,
    title: "거래 제목 텍스트 텍스트",
    content:
      "거래 설명 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 ",
    user: {
      name: "사용자 이름",
      profileUrl: "",
    },
    createdAt: "4시간 전",
    number: 89,
  },
  {
    id: 2,
    txType: Tx.Refund,
    amount: 50000,
    title: "거래 제목 텍스트 텍스트",
    content:
      "거래 설명 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 ",
    user: {
      name: "사용자 이름",
      profileUrl: "",
    },
    createdAt: "4시간 전",
    number: 89,
  },
  {
    id: 3,
    txType: Tx.Outcome,
    amount: 50000,
    title: "거래 제목 텍스트 텍스트",
    content: "거래 설명 텍스트 텍스트 텍스트",
    user: {
      name: "사용자 이름",
      profileUrl: "",
    },
    createdAt: "4시간 전",
    number: 89,
  },
  {
    id: 4,
    txType: Tx.Refund,
    amount: 50000,
    title: "거래 제목 텍스트 텍스트",
    content:
      "거래 설명 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 텍스트 ",
    user: {
      name: "사용자 이름",
      profileUrl: "",
    },
    createdAt: "4시간 전",
    number: 89,
  },
];

export default function ContentSection() {
  const [dataList, setDataList] = useState(feedData);
  return (
    <ContentStyle>
      <div className="content-title-wrap">
        <div className="content-title">
          <p>티끌 모아 태산 ⛰</p>
          <p>지출을 기록하고, 조언을 받아보세요!</p>
          <CTAButton
            size={"lg"}
            icon={<Edit3 />}
            children={"기록하기"}
            className="title-button"
          />
        </div>
      </div>
      <Navigation />
      <Feeds dataList={dataList} />
    </ContentStyle>
  );
}
