import { useState } from "react";
import { Edit3 } from "react-feather";

import { FeedsStyle } from "@/styles/components/FeedsStyle";
import { Tx } from "@/types";

import Feeds from "./Feed/Feeds";
import Navigation from "./Navigation";
import CTAButton from "../atoms/CTAButton/CTAButton";

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
    <div className="content-wrap">
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
      <FeedsStyle>
        <Feeds dataList={dataList} />
      </FeedsStyle>
    </div>
  );
}
