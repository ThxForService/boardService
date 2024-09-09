package com.thxforservice.board.services;

import com.querydsl.core.BooleanBuilder;
import com.thxforservice.board.entities.WishList;
import com.thxforservice.board.repositories.WishListRepository;
import com.thxforservice.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import com.thxforservice.board.entities.QWishList;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final MemberUtil memberUtil;
    private final WishListRepository repository;

    public void add(Long seq) {
        if (!memberUtil.isLogin()) {
            return;
        }

        WishList wishList = new WishList();
        wishList.setSeq(seq);
        repository.saveAndFlush(wishList);
    }

    public void remove(Long seq) {
        if (!memberUtil.isLogin()) {
            return;
        }
        repository.deleteById(seq);
        repository.flush();
    }

    public List<Long> getList() {
        if (!memberUtil.isLogin()) {
            return null;
        }

        QWishList wishList = QWishList.wishList;

        List<Long> items = ((List<WishList>)repository.findAll(wishList.email.eq(memberUtil.getMember().getEmail()), Sort.by(desc("createdAt")))).stream().map(WishList::getSeq).toList();


        return items;
    }

    public boolean check(Long seq) {
        if (memberUtil.isLogin()) {
            BooleanBuilder builder = new BooleanBuilder();
            QWishList wishList = QWishList.wishList;
            builder.and(wishList.seq.eq(seq))
                    .and(wishList.email.eq(memberUtil.getMember().getEmail()));

            return repository.exists(builder);
        }

        return false;
    }
}
